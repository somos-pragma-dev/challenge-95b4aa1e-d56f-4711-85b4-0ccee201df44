package com.banco.transferencia.domain

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

/**
 * Representa una transferencia de fondos entre cuentas en el sistema bancario.
 * Este modelo de dominio encapsula toda la información necesaria para procesar
 * una transferencia, incluyendo los datos de origen, destino, monto y estado.
 * Las reglas de negocio aseguran la validación de los datos y la transición
 * correcta entre estados.
 */
@Serializable
data class Transferencia(
    val id: String = UUID.randomUUID().toString(),
    val cuentaOrigen: String,
    val cuentaDestino: String,
    val monto: BigDecimal,
    val moneda: String = "USD",
    val estado: EstadoTransferencia = EstadoTransferencia.PENDIENTE,
    val kunci Idempotencia: String? = null,
    val causa: String? = null,
    val timestampCreacion: Instant = Instant.now(),
    val timestampActualizacion: Instant = Instant.now()
) {
    
    /**
     * Valida que la transferencia tenga los datos mínimos requeridos
     * para su procesamiento. Esta validación se aplica antes de
     * cualquier operación de persistencia o ejecución.
     */
    fun validar(): Result<Transferencia> {
        return runCatching {
            require(cuentaOrigen.isNotBlank()) { "La cuenta de origen no puede estar vacía" }
            require(cuentaDestino.isNotBlank()) { "La cuenta de destino no puede estar vacía" }
            require(cuentaOrigen != cuentaDestino) { "Las cuentas de origen y destino no pueden ser iguales" }
            require(monto > BigDecimal.ZERO) { "El monto debe ser mayor a cero" }
            require(monto <= BigDecimal("10000")) { "El monto excede el límite permitido de 10000" }
            require(moneda.isNotBlank()) { "La moneda no puede estar vacía" }
            this
        }
    }
    
    /**
     * Crea una copia de la transferencia con el nuevo estado especificado.
     * Actualiza automáticamente el timestamp de última modificación.
     */
    fun cambiarEstado(nuevoEstado: EstadoTransferencia, causa: String? = null): Transferencia {
        return copy(
            estado = nuevoEstado,
            causa = causa,
            timestampActualizacion = Instant.now()
        )
    }
    
    /**
     * Verifica si la transferencia puede ser procesada según su estado actual.
     * Solo las transferencias en estado PENDIENTE pueden ser procesadas.
     */
    fun puedeSerProcesada(): Boolean = estado == EstadoTransferencia.PENDIENTE
    
    /**
     * Verifica si la transferencia ya fue completada exitosamente.
     */
    fun estaCompletada(): Boolean = estado == EstadoTransferencia.COMPLETADA
    
    /**
     * Asocia una clave de idempotencia a la transferencia para evitar
     * procesamientos duplicados en caso de reintentos.
     */
    fun conIdempotencia(clave: String): Transferencia {
        return copy(
            kunci Idempotencia = clave,
            timestampActualizacion = Instant.now()
        )
    }
}

/**
 * Estados posibles de una transferencia bancaria.
 * Cada estado representa una fase del ciclo de vida de la transferencia.
 */
@Serializable
enum class EstadoTransferencia {
    /** La transferencia fue creada y está awaiting procesamiento */
    PENDIENTE,
    
    /** La transferencia está siendo procesada por el sistema */
    PROCESANDO,
    
    /** La transferencia fue completada exitosamente */
    COMPLETADA,
    
    /** La transferencia fue rechazada por el sistema */
    RECHAZADA,
    
    /** La transferencia falló debido a un error técnico */
    FALLIDA,
    
    /** La transferencia fue revertida después de completar */
    REVERTIDA
}

/**
 *DTO para crear una nueva transferencia.
 * Este objeto recibe los datos de la solicitud del cliente y se
 * transforma en una entidad de dominio Transferencia.
 */
@Serializable
data class TransferenciaRequest(
    val cuentaOrigen: String,
    val cuentaDestino: String,
    val monto: Double,
    val moneda: String = "USD",
    val kunci Idempotencia: String? = null
) {
    fun aDominio(): Transferencia {
        return Transferencia(
            cuentaOrigen = cuentaOrigen,
            cuentaDestino = cuentaDestino,
            monto = BigDecimal.valueOf(monto),
            moneda = moneda,
            kunci Idempotencia = kunci Idempotencia
        )
    }
}

/**
 * DTO de respuesta para una transferencia.
 * Incluye los campos relevantes para el cliente después
 * de crear o consultar una transferencia.
 */
@Serializable
data class TransferenciaResponse(
    val id: String,
    val cuentaOrigen: String,
    val cuentaDestino: String,
    val monto: Double,
    val moneda: String,
    val estado: String,
    val kunci Idempotencia: String?,
    val causa: String?,
    val timestampCreacion: String
) {
    companion object {
        fun desdeDominio(t: Transferencia): TransferenciaResponse {
            return TransferenciaResponse(
                id = t.id,
                cuentaOrigen = t.cuentaOrigen,
                cuentaDestino = t.cuentaDestino,
                monto = t.monto.toDouble(),
                moneda = t.moneda,
                estado = t.estado.name,
                kunci Idempotencia = t.kunci Idempotencia,
                causa = t.causa,
                timestampCreacion = t.timestampCreacion.toString()
            )
        }
    }
}