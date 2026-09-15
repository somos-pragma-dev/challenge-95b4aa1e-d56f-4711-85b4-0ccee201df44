package com.banco.transferencia.infrastructure.clients

import com.banco.transferencia.infrastructure.observability.Metrics
import com.banco.transferencia.infrastructure.observability.Traces
import com.banco.transferencia.infrastructure.observability.Logs
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

data class NotificacionRequest(
    val destinatario: String,
    val mensaje: String,
    val tipo: String,
    val canal: String = "SMS",
    val prioridad: String = "NORMAL"
)

data class NotificacionResponse(
    val exitosa: Boolean,
    val mensajeId: String? = null,
    val mensaje: String
)

class NotificacionClient(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val metrics: Metrics,
    private val traces: Traces,
    private val logs: Logs
) {
    suspend fun enviarNotificacion(cuenta: String, mensaje: String, tipo: String): Result<NotificacionResponse> = withContext(Dispatchers.IO) {
        val traceId = traces.generarTraceId()
        logs.registrar("Enviando notificacion", mapOf(
            "traceId" to traceId,
            "cuenta" to cuenta,
            "tipo" to tipo,
            "mensaje" to mensaje.take(100)
        ))
        
        try {
            val request = NotificacionRequest(
                destinatario = cuenta,
                mensaje = mensaje,
                tipo = tipo,
                canal = determinarCanal(tipo),
                prioridad = determinarPrioridad(tipo)
            )
            
            val response = httpClient.post("$baseUrl/notificaciones/enviar") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            
            val resultado = response.body<NotificacionResponse>()
            
            if (resultado.exitosa) {
                logs.registrar("Notificacion enviada exitosamente", mapOf(
                    "traceId" to traceId,
                    "mensajeId" to (resultado.mensajeId ?: "N/A"),
                    "tipo" to tipo
                ))
                metrics.incrementar("notificaciones.enviada")
                metrics.incrementar("notificaciones.$tipo.enviada")
                Result.success(resultado)
            } else {
                logs.registrar("Notificacion fallida", mapOf(
                    "traceId" to traceId,
                    "mensaje" to resultado.mensaje,
                    "tipo" to tipo
                ))
                metrics.incrementar("notificaciones.fallida")
                Result.failure(Exception(resultado.mensaje))
            }
        } catch (e: Exception) {
            logs.registrar("Error al enviar notificacion", mapOf(
                "traceId" to traceId,
                "error" to e.message,
                "tipoError" to e::class.simpleName ?: "Unknown"
            ))
            metrics.incrementar("notificaciones.error")
            Result.failure(e)
        }
    }
    
    suspend fun enviarNotificacionMultiple(cuenta: String, mensajes: List<String>, tipo: String): Result<List<NotificacionResponse>> = withContext(Dispatchers.IO) {
        val traceId = traces.generarTraceId()
        logs.registrar("Enviando notificaciones multiples", mapOf(
            "traceId" to traceId,
            "cuenta" to cuenta,
            "cantidad" to mensajes.size.toString()
        ))
        
        try {
            val resultados = mensajes.map { msg ->
                val request = NotificacionRequest(
                    destinatario = cuenta,
                    mensaje = msg,
                    tipo = tipo,
                    canal = determinarCanal(tipo),
                    prioridad = determinarPrioridad(tipo)
                )
                
                val response = httpClient.post("$baseUrl/notificaciones/enviar") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
                
                response.body<NotificacionResponse>()
            }
            
            val exitosas = resultados.count { it.exitosa }
            logs.registrar("Notificaciones multiples completadas", mapOf(
                "traceId" to traceId,
                "total" to resultados.size.toString(),
                "exitosas" to exitosas.toString(),
                "fallidas" to (resultados.size - exitosas).toString()
            ))
            
            metrics.incrementar("notificaciones.multiples.enviada", resultados.size.toLong())
            Result.success(resultados)
        } catch (e: Exception) {
            logs.registrar("Error en notificaciones multiples", mapOf(
                "traceId" to traceId,
                "error" to e.message
            ))
            metrics.incrementar("notificaciones.multiples.error")
            Result.failure(e)
        }
    }
    
    private fun determinarCanal(tipo: String): String {
        return when (tipo) {
            "ALERTA" -> "EMAIL"
            "FRAUDE" -> "SMS"
            "DEBITO", "CREDITO" -> "PUSH"
            else -> "SMS"
        }
    }
    
    private fun determinarPrioridad(tipo: String): String {
        return when (tipo) {
            "FRAUDE" -> "URGENTE"
            "ALERTA" -> "ALTA"
            else -> "NORMAL"
        }
    }
}