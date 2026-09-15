package com.banco.transferencia.infrastructure.clients

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

data class CuentaDto(
    val cuentaId: String,
    val titular: String?,
    val tipo: String?,
    val saldo: BigDecimal?,
    val estado: String?,
    val fechaApertura: String?
)

data class MovimientoDto(
    val cuentaId: String,
    val monto: BigDecimal,
    val tipo: String,
    val referencia: String,
    val fecha: String
)

data class ResultadoOperacionCuenta(
    val success: Boolean,
    val nuevoSaldo: BigDecimal? = null,
    val error: String? = null
)

class CuentasClient(
    private val httpClient: HttpClient,
    private val baseUrl: String
) {
    suspend fun debitarCuenta(cuenta: String, monto: BigDecimal, referencia: String): Result<ResultadoOperacionCuenta> = withContext(Dispatchers.IO) {
        try {
            val response = httpClient.post("$baseUrl/cuentas/debitar") {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "cuentaId" to cuenta,
                    "monto" to monto,
                    "referencia" to referencia
                ))
            }
            if (response.status.value in 200..299) {
                val body = response.body<Map<String, Any>>()
                Result.success(
                    ResultadoOperacionCuenta(
                        success = body["success"] as? Boolean ?: false,
                        nuevoSaldo = (body["nuevoSaldo"] as? Number)?.toBigDecimal(),
                        error = body["error"] as? String
                    )
                )
            } else {
                Result.success(ResultadoOperacionCuenta(success = false, error = "HTTP ${response.status.value}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun creditarCuenta(cuenta: String, monto: BigDecimal, referencia: String): Result<ResultadoOperacionCuenta> = withContext(Dispatchers.IO) {
        try {
            val response = httpClient.post("$baseUrl/cuentas/acreditar") {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "cuentaId" to cuenta,
                    "monto" to monto,
                    "referencia" to referencia
                ))
            }
            if (response.status.value in 200..299) {
                val body = response.body<Map<String, Any>>()
                Result.success(
                    ResultadoOperacionCuenta(
                        success = body["success"] as? Boolean ?: false,
                        nuevoSaldo = (body["nuevoSaldo"] as? Number)?.toBigDecimal(),
                        error = body["error"] as? String
                    )
                )
            } else {
                Result.success(ResultadoOperacionCuenta(success = false, error = "HTTP ${response.status.value}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun acreditarCuenta(cuenta: String, monto: BigDecimal): Boolean {
        return creditarCuenta(cuenta, monto, "").getOrNull()?.success ?: false
    }

    suspend fun consultarSaldo(cuenta: String): Result<BigDecimal> = withContext(Dispatchers.IO) {
        try {
            val response = httpClient.get("$baseUrl/cuentas/$cuenta/saldo")
            if (response.status.value in 200..299) {
                val body = response.body<Map<String, Any>>()
                val saldo = (body["saldo"] as? Number)?.toBigDecimal()
                if (saldo != null) {
                    Result.success(saldo)
                } else {
                    Result.failure(Exception("Saldo no disponible"))
                }
            } else {
                Result.failure(Exception("Error al consultar saldo: ${response.status.value}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerSaldo(cuenta: String): Result<BigDecimal> {
        return consultarSaldo(cuenta)
    }

    suspend fun validarCuenta(cuenta: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val response = httpClient.get("$baseUrl/cuentas/$cuenta/existe")
            if (response.status.value in 200..299) {
                val body = response.body<Map<String, Any>>()
                Result.success(body["existe"] as? Boolean ?: false)
            } else if (response.status.value == 404) {
                Result.success(false)
            } else {
                Result.failure(Exception("Error al validar cuenta: ${response.status.value}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verificarCuenta(cuenta: String): Result<Boolean> {
        return validarCuenta(cuenta)
    }

    suspend fun obtenerInformacionCuenta(cuenta: String): Result<CuentaDto> = withContext(Dispatchers.IO) {
        try {
            val response = httpClient.get("$baseUrl/cuentas/$cuenta")
            if (response.status.value in 200..299) {
                val body = response.body<Map<String, Any>>()
                Result.success(
                    CuentaDto(
                        cuentaId = body["cuentaId"] as? String ?: cuenta,
                        titular = body["titular"] as? String,
                        tipo = body["tipo"] as? String,
                        saldo = (body["saldo"] as? Number)?.toBigDecimal(),
                        estado = body["estado"] as? String,
                        fechaApertura = body["fechaApertura"] as? String
                    )
                )
            } else {
                Result.failure(Exception("Cuenta no encontrada"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}