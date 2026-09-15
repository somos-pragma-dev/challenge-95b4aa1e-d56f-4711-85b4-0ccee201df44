package com.banco.transferencia.infrastructure

import com.banco.transferencia.domain.Transferencia
import com.banco.transferencia.domain.TransferenciaRepository
import com.banco.transferencia.domain.TransferenciaRequest
import com.banco.transferencia.domain.TransferenciaResponse
import com.banco.transferencia.domain.EstadoTransferencia
import com.banco.transferencia.infrastructure.observability.Traces
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.request.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.path
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.UUID

class TransferenciaController(
    private val transferenciaRepository: TransferenciaRepository
) {
    private val logger = LoggerFactory.getLogger(TransferenciaController::class.java)
    private val tracer = Traces.obtenerTracer()

    fun registerRoutes(routing: Routing) {
        logger.info("Registrando rutas del controlador de transferencias")

        routing.route("/api/v1/transferencias") {
            post("/") { ejecutarTransferencia(call) }
            get("/") { listarTransferencias(call) }
            get("/{id}") { obtenerTransferencia(call) }
            get("/cuenta/{cuentaOrigen}") { listarPorCuentaOrigen(call) }
            put("/{id}/cancelar") { cancelarTransferencia(call) }
        }

        logger.info("Rutas registradas exitosamente")
    }

    private suspend fun ejecutarTransferencia(call: ApplicationCall) {
        val span = tracer.spanBuilder("POST /transferencias").startSpan()
        try {
            val idempotencyKey = call.request.header("Idempotency-Key")
            logger.info("Recibida solicitud de transferencia con clave de idempotencia: $idempotencyKey")

            if (idempotencyKey.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Header Idempotency-Key es requerido"))
                return
            }

            val existente = transferenciaRepository.buscarPorClaveIdempotencia(idempotenciaKey)
            if (existente != null) {
                logger.info("Transferencia existente encontrada para clave: $idempotenciaKey")
                call.respond(HttpStatusCode.OK, TransferenciaResponse.fromDominio(existente))
                return
            }

            val request = call.receive<TransferenciaRequest>()
            val transferencia = request.aDominio().conIdempotencia(idempotenciaKey)

            val validacion = transferencia.validar()
            if (validacion.isFailure) {
                val error = validacion.exceptionOrNull()?.message ?: "Error de validación"
                logger.warn("Validación fallida: $error")
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to error))
                return
            }

            val guardada = transferenciaRepository.guardar(transferencia)
            logger.info("Transferencia creada exitosamente con ID: ${guardada.id}")

            call.respond(HttpStatusCode.Created, TransferenciaResponse.fromDominio(guardada))
        } catch (e: BadRequestException) {
            logger.error("Solicitud malformada: ${e.message}")
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
        } catch (e: Exception) {
            logger.error("Error procesando transferencia: ${e.message}", e)
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error interno del servidor"))
        } finally {
            span.end()
        }
    }

    private suspend fun obtenerTransferencia(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de transferencia requerido"))
            return
        }

        val transferencia = transferenciaRepository.buscarPorId(id)
        if (transferencia == null) {
            logger.warn("Transferencia no encontrada: $id")
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Transferencia no encontrada"))
            return
        }

        call.respond(HttpStatusCode.OK, TransferenciaResponse.fromDominio(transferencia))
    }

    private suspend fun listarTransferencias(call: ApplicationCall) {
        val desdeStr = call.request.queryParameters["desde"]
        val hastaStr = call.request.queryParameters["hasta"]

        val transferencias = if (desdeStr != null && hastaStr != null) {
            val desde = Instant.parse(desdeStr)
            val hasta = Instant.parse(hastaStr)
            transferenciaRepository.listarPorRangoDeTiempo(desde, hasta)
        } else {
            emptyList()
        }

        val response = transferencias.map { TransferenciaResponse.fromDominio(it) }
        call.respond(HttpStatusCode.OK, response)
    }

    private suspend fun listarPorCuentaOrigen(call: ApplicationCall) {
        val cuentaOrigen = call.parameters["cuentaOrigen"]
        if (cuentaOrigen.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Número de cuenta requerido"))
            return
        }

        val transferencias = transferenciaRepository.listarPorCuentaOrigen(cuentaOrigen)
        val response = transferencias.map { TransferenciaResponse.fromDominio(it) }
        call.respond(HttpStatusCode.OK, response)
    }

    private suspend fun cancelarTransferencia(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de transferencia requerido"))
            return
        }

        val transferencia = transferenciaRepository.buscarPorId(id)
        if (transferencia == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Transferencia no encontrada"))
            return
        }

        if (!transferencia.puedeSerProcesada()) {
            call.respond(HttpStatusCode.Conflict, mapOf("error" to "La transferencia no puede ser cancelada en estado: ${transferencia.estado}"))
            return
        }

        val cancelada = transferencia.cambiarEstado(EstadoTransferencia.CANCELADA, "Cancelada por el usuario")
        val actualizada = transferenciaRepository.actualizar(cancelada)

        logger.info("Transferencia cancelada: $id")
        call.respond(HttpStatusCode.OK, TransferenciaResponse.fromDominio(actualizada))
    }
}