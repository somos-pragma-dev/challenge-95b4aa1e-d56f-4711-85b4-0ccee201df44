package com.banco.transferencia.infrastructure.persistence

import com.banco.transferencia.domain.EstadoTransferencia
import com.banco.transferencia.domain.Transferencia
import com.banco.transferencia.domain.TransferenciaRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.math.BigDecimal
import java.time.Instant

object TransferenciaTable : Table("transferencias") {
    val id = varchar("id", 36).uniqueIndex()
    val cuentaOrigen = varchar("cuenta_origen", 20)
    val cuentaDestino = varchar("cuenta_destino", 20)
    val monto = decimal("monto", 19, 4)
    val estado = varchar("estado", 30)
    val causa = varchar("causa", 500).nullable()
    val claveIdempotencia = varchar("clave_idempotencia", 100).nullable().uniqueIndex()
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
    val completedAt = timestamp("completed_at").nullable()

    override val primaryKey = PrimaryKey(id)
}

class TransferenciaRepositoryImpl(
    private val database: Database
) : TransferenciaRepository {

    override suspend fun guardar(transferencia: Transferencia): Transferencia {
        return transaction(database) {
            val now = Instant.now()
            TransferenciaTable.insert { row ->
                row[id] = transferencia.id
                row[cuentaOrigen] = transferencia.cuentaOrigen
                row[cuentaDestino] = transferencia.cuentaDestino
                row[monto] = transferencia.monto
                row[estado] = transferencia.estado.name
                row[causa] = transferencia.causa
                row[claveIdempotencia] = transferencia.claveIdempotencia
                row[createdAt] = now
                row[updatedAt] = now
                row[completedAt] = transferencia.completedAt
            }
            transferencia
        }
    }

    override suspend fun actualizar(transferencia: Transferencia): Transferencia {
        return transaction(database) {
            val now = Instant.now()
            TransferenciaTable.update({ TransferenciaTable.id eq transferencia.id }) { row ->
                row[cuentaOrigen] = transferencia.cuentaOrigen
                row[cuentaDestino] = transferencia.cuentaDestino
                row[monto] = transferencia.monto
                row[estado] = transferencia.estado.name
                row[causa] = transferencia.causa
                row[claveIdempotencia] = transferencia.claveIdempotencia
                row[updatedAt] = now
                row[completedAt] = transferencia.completedAt
            }
            transferencia
        }
    }

    override suspend fun buscarPorId(id: String): Transferencia? {
        return transaction(database) {
            TransferenciaTable.select { TransferenciaTable.id eq id }
                .map { it.toTransferencia() }
                .firstOrNull()
        }
    }

    override suspend fun buscarPorClaveIdempotencia(clave: String): Transferencia? {
        return transaction(database) {
            TransferenciaTable.select { TransferenciaTable.claveIdempotencia eq clave }
                .map { it.toTransferencia() }
                .firstOrNull()
        }
    }

    override suspend fun listarPorCuentaOrigen(cuentaOrigen: String): List<Transferencia> {
        return transaction(database) {
            TransferenciaTable.select { TransferenciaTable.cuentaOrigen eq cuentaOrigen }
                .map { it.toTransferencia() }
        }
    }

    override suspend fun listarPorCuentaDestino(cuentaDestino: String): List<Transferencia> {
        return transaction(database) {
            TransferenciaTable.select { TransferenciaTable.cuentaDestino eq cuentaDestino }
                .map { it.toTransferencia() }
        }
    }

    override suspend fun listarPorRangoDeTiempo(desde: Instant, hasta: Instant): List<Transferencia> {
        return transaction(database) {
            TransferenciaTable.select {
                TransferenciaTable.createdAt.greaterEq(desde) and TransferenciaTable.createdAt.lessEq(hasta)
            }.map { it.toTransferencia() }
        }
    }

    override suspend fun contarPorEstado(estado: EstadoTransferencia): Int {
        return transaction(database) {
            TransferenciaTable.select { TransferenciaTable.estado eq estado.name }
                .count()
                .toInt()
        }
    }

    private fun org.jetbrains.exposed.sql.ResultRow.toTransferencia(): Transferencia {
        return Transferencia(
            id = this[TransferenciaTable.id],
            cuentaOrigen = this[TransferenciaTable.cuentaOrigen],
            cuentaDestino = this[TransferenciaTable.cuentaDestino],
            monto = this[TransferenciaTable.monto],
            estado = EstadoTransferencia.valueOf(this[TransferenciaTable.estado]),
            causa = this[TransferenciaTable.causa],
            claveIdempotencia = this[TransferenciaTable.claveIdempotencia],
            createdAt = this[TransferenciaTable.createdAt].toInstant(),
            updatedAt = this[TransferenciaTable.updatedAt].toInstant(),
            completedAt = this[TransferenciaTable.completedAt]?.toInstant()
        )
    }
}