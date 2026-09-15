package com.banco.transferencia.application

import com.banco.transferencia.domain.Transferencia
import com.banco.transferencia.domain.TransferenciaRepository
import com.banco.transferencia.domain.TransferenciaRequest
import com.banco.transferencia.domain.TransferenciaResponse
import com.banco.transferencia.domain.EstadoTransferencia
import com.banco.transferencia.infrastructure.clients.CuentasClient
import com.banco.transferencia.infrastructure.clients.NotificacionClient
import com.banco.transferencia.infrastructure.observability.Metrics
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.time.Instant

class TransferenciaService(
    private val repository: TransferenciaRepository,
    private val cuentasClient: CuentasClient,
    private val notificacionClient: NotificacionClient,
    private val httpClient: HttpClient
) {
    private val metrics = Metrics

    suspend fun crearTransferencia(request: TransferenciaRequest, claveIdempotencia: String?): Result<TransferenciaResponse> = withContext(Dispatchers.IO) {
        metrics.registrarTransferenciaIniciada()
        val clave = claveIdempotencia ?: java.util.UUID.randomUUID().toString()
        
        val existente = repository.buscarPorClaveIdempotencia(clave)
        if (existente != null) {
            metrics.registrarIdempotenciaHit()
            return@withContext Result.success(existente.aRespuesta())
        }
        metrics.registrarIdempotenciaMiss()

        val transferencia = request.aDominio().copy(
            claveIdempotencia = clave,
            createdAt = Instant.now()
        )

        val validacion = transferencia.validar()
        if (validacion.isFailure) {
            metrics.registrarTransferenciaRechazada()
            return@withContext Result.failure(validacion.exceptionOrNull()!!)
        }

        val guardada = repository.guardar(transferencia)
        
        val debitoExitoso = cuentasClient.debitarCuenta(
            transferencia.cuentaOrigen,
            transferencia.monto,
            transferencia.id
        ).getOrNull() ?: false

        if (!debitoExitoso) {
            val fallida = guardada.cambiarEstado(EstadoTransferencia.FALLIDA, "Debito fallido")
            repository.actualizar(fallida)
            metrics.registrarTransferenciaFallida()
            return@withContext Result.success(fallida.aRespuesta())
        }

        val creditoExitoso = cuentasClient.creditarCuenta(
            transferencia.cuentaDestino,
            transferencia.monto,
            transferencia.id
        ).getOrNull() ?: false

        val completada = if (creditoExitoso) {
            guardada.cambiarEstado(EstadoTransferencia.COMPLETADA)
        } else {
            guardada.cambiarEstado(EstadoTransferencia.FALLIDA, "Credito fallido")
        }
        
        repository.actualizar(completada)
        metrics.registrarTransferenciaExitosa()
        metrics.registrarMonto(transferencia.monto.toDouble())

        try {
            notificacionClient.enviarNotificacion(
                transferencia.cuentaOrigen,
                "Transferencia ${completada.estado}: ${transferencia.monto}",
                "TRANSFERENCIA"
            )
        } catch (e: Exception) {
            // No bloquea la operación
        }

        Result.success(completada.aRespuesta())
    }

    suspend fun procesarTransferencia(request: TransferenciaRequest, claveIdempotencia: String): Result<TransferenciaResponse> {
        return crearTransferencia(request, claveIdempotencia)
    }

    suspend fun buscarTransferenciaPorId(id: String): Result<Transferencia> = withContext(Dispatchers.IO) {
        val transferencia = repository.buscarPorId(id)
        if (transferencia != null) {
            Result.success(transferencia)
        } else {
            Result.failure(NoSuchElementException("Transferencia no encontrada: $id"))
        }
    }

    suspend fun listarTransferenciasPorCuentaOrigen(cuentaOrigen: String): Result<List<Transferencia>> = withContext(Dispatchers.IO) {
        Result.success(repository.listarPorCuentaOrigen(cuentaOrigen))
    }

    suspend fun listarTransferenciasPorRangoDeTiempo(desde: Instant, hasta: Instant): Result<List<Transferencia>> = withContext(Dispatchers.IO) {
        Result.success(repository.listarPorRangoDeTiempo(desde, hasta))
    }

    suspend fun contarTransferenciasPorEstado(estado: EstadoTransferencia): Result<Int> = withContext(Dispatchers.IO) {
        Result.success(repository.contarPorEstado(estado))
    }

    suspend fun consultarTransferencia(id: String): Transferencia? = withContext(Dispatchers.IO) {
        repository.buscarPorId(id)
    }

    suspend fun listarTransferenciasPorOrigen(cuentaOrigen: String): List<Transferencia> = withContext(Dispatchers.IO) {
        repository.listarPorCuentaOrigen(cuentaOrigen)
    }

    suspend fun listarTransferenciasPorDestino(cuentaDestino: String): List<Transferencia> = withContext(Dispatchers.IO) {
        repository.listarPorCuentaDestino(cuentaDestino)
    }

    suspend fun listarTransferenciasPorTiempo(desde: Instant, hasta: Instant): List<Transferencia> = withContext(Dispatchers.IO) {
        repository.listarPorRangoDeTiempo(desde, hasta)
    }

    private fun Transferencia.aRespuesta(): TransferenciaResponse {
        return TransferenciaResponse(
            id = this.id,
            cuentaOrigen = this.cuentaOrigen,
            cuentaDestino = this.cuentaDestino,
            monto = this.monto,
            descripcion = this.descripcion,
            estado = this.estado,
            claveIdempotencia = this.claveIdempotencia,
            createdAt = this.createdAt.toString()
        )
    }
}