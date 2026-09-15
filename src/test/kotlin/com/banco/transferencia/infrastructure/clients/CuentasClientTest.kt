package com.banco.transferencia.infrastructure.clients

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import org.jetbrains.kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import java.math.BigDecimal

class CuentasClientTest {

    private lateinit var wireMockServer: WireMockServer
    private lateinit var httpClient: HttpClient
    private lateinit var cuentasClient: CuentasClient

    companion object {
        private const val BASE_URL = "http://localhost:8089"
    }

    @BeforeEach
    fun setUp() {
        wireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().port(8089))
        wireMockServer.start()
        WireMock.configureFor("localhost", 8089)

        httpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
        cuentasClient = CuentasClient(httpClient, BASE_URL)
    }

    @AfterEach
    fun tearDown() {
        wireMockServer.stop()
        httpClient.close()
    }

    @Test
    fun `debitarCuenta con respuesta exitosa retorna true`() = runTest {
        wireMockServer.stubFor(
            WireMock.post("/cuentas/debitar")
                .withRequestBody(WireMock.equalToJson("""
                    {
                        "cuentaId": "CUENTA_123",
                        "monto": 500.00
                    }
                """.trimIndent()))
                .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "success": true,
                            "nuevoSaldo": 1500.00
                        }
                    """.trimIndent()))
        )

        val resultado = cuentasClient.debitarCuenta("CUENTA_123", BigDecimal("500.00"))

        assertTrue(resultado)
    }

    @Test
    fun `debitarCuenta con respuesta fallida retorna false`() = runTest {
        wireMockServer.stubFor(
            WireMock.post("/cuentas/debitar")
                .willReturn(WireMock.aResponse()
                    .withStatus(400)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "success": false,
                            "error": "Saldo insuficiente"
                        }
                    """.trimIndent()))
        )

        val resultado = cuentasClient.debitarCuenta("CUENTA_SIN_SALDO", BigDecimal("10000.00"))

        assertFalse(resultado)
    }

    @Test
    fun `acreditarCuenta con respuesta exitosa retorna true`() = runTest {
        wireMockServer.stubFor(
            WireMock.post("/cuentas/acreditar")
                .withRequestBody(WireMock.equalToJson("""
                    {
                        "cuentaId": "CUENTA_DESTINO_456",
                        "monto": 300.00
                    }
                """.trimIndent()))
                .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "success": true,
                            "nuevoSaldo": 2300.00
                        }
                    """.trimIndent()))
        )

        val resultado = cuentasClient.acreditarCuenta("CUENTA_DESTINO_456", BigDecimal("300.00"))

        assertTrue(resultado)
    }

    @Test
    fun `acreditarCuenta con cuenta inexistente retorna false`() = runTest {
        wireMockServer.stubFor(
            WireMock.post("/cuentas/acreditar")
                .willReturn(WireMock.aResponse()
                    .withStatus(404)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "success": false,
                            "error": "Cuenta no encontrada"
                        }
                    """.trimIndent()))
        )

        val resultado = cuentasClient.acreditarCuenta("CUENTA_INEXISTENTE", BigDecimal("100.00"))

        assertFalse(resultado)
    }

    @Test
    fun `obtenerSaldo retorna el saldo correcto de la cuenta`() = runTest {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/cuentas/CUENTA_789/saldo"))
                .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "cuentaId": "CUENTA_789",
                            "saldo": 5000.50,
                            "ultimaActualizacion": "2024-01-15T14:30:00Z"
                        }
                    """.trimIndent()))
        )

        val resultado = cuentasClient.obtenerSaldo("CUENTA_789")

        assertNotNull(resultado)
        assertEquals(BigDecimal("5000.50"), resultado)
    }

    @Test
    fun `obtenerSaldo con error de servidor lanza excepcion`() = runTest {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/cuentas/CUENTA_ERROR/saldo"))
                .willReturn(WireMock.aResponse()
                    .withStatus(500)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "error": "Error interno del servidor"
                        }
                    """.trimIndent()))
        )

        val resultado = cuentasClient.obtenerSaldo("CUENTA_ERROR")

        assertTrue(resultado.isFailure)
    }

    @Test
    fun `debitarCuenta con timeout retorna failure`() = runTest {
        wireMockServer.stubFor(
            WireMock.post("/cuentas/debitar")
                .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withFixedDelay(10000))
        )

        val resultado = cuentasClient.debitarCuenta("CUENTA_TIMEOUT", BigDecimal("100.00"))

        assertTrue(resultado.isFailure)
    }

    @Test
    fun `verificarCuenta existe retorna true para cuenta valida`() = runTest {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/cuentas/CUENTA_VALIDA/existe"))
                .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "existe": true,
                            "cuentaId": "CUENTA_VALIDA",
                            "estado": "ACTIVA"
                        }
                    """.trimIndent()))
        )

        val resultado = cuentasClient.verificarCuenta("CUENTA_VALIDA")

        assertTrue(resultado.getOrNull() ?: false)
    }

    @Test
    fun `verificarCuenta inexistente retorna false`() = runTest {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/cuentas/CUENTA_INEXistente/existe"))
                .willReturn(WireMock.aResponse()
                    .withStatus(404)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "existe": false
                        }
                    """.trimIndent()))
        )

        val resultado = cuentasClient.verificarCuenta("CUENTA_INEXistente")

        assertFalse(resultado.getOrNull() ?: true)
    }

    @Test
    fun `obtenerInformacionCuenta retorna datos completos de la cuenta`() = runTest {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/cuentas/CUENTA_INFO"))
                .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "cuentaId": "CUENTA_INFO",
                            "titular": "Juan Perez",
                            "tipo": "CUENTA_CORRIENTE",
                            "saldo": 10000.00,
                            "estado": "ACTIVA",
                            "fechaApertura": "2023-01-01T00:00:00Z"
                        }
                    """.trimIndent()))
        )

        val resultado = cuentasClient.obtenerInformacionCuenta("CUENTA_INFO")

        assertTrue(resultado.isSuccess)
        val info = resultado.getOrNull()
        assertNotNull(info)
    }

    @Test
    fun `debitarCuenta con monto mayor al saldo retorna false`() = runTest {
        wireMockServer.stubFor(
            WireMock.post("/cuentas/debitar")
                .willReturn(WireMock.aResponse()
                    .withStatus(422)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "success": false,
                            "error": "Monto mayor al saldo disponible"
                        }
                    """.trimIndent()))
        )

        val resultado = cuentasClient.debitarCuenta("CUENTA_SALDO_BAJO", BigDecimal("50000.00"))

        assertFalse(resultado)
    }

    @Test
    fun `retry en caso de fallo temporal`() = runTest {
        var intentos = 0
        wireMockServer.stubFor(
            WireMock.post("/cuentas/debitar")
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "error": "Error temporal"
                            }
                        """.trimIndent())
                )
        )

        val resultado = cuentasClient.debitarCuenta("CUENTA_RETRY", BigDecimal("50.00"))

        assertTrue(resultado.isFailure || !resultado)
    }

    @Test
    fun `multiple operations maintain session`() = runTest {
        wireMockServer.stubFor(
            WireMock.post("/cuentas/debitar")
                .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "success": true,
                            "nuevoSaldo": 4500.00
                        }
                    """.trimIndent()))
        )
        wireMockServer.stubFor(
            WireMock.post("/cuentas/acreditar")
                .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "success": true,
                            "nuevoSaldo": 5500.00
                        }
                    """.trimIndent()))
        )

        val debito = cuentasClient.debitarCuenta("CUENTA_SESSION", BigDecimal("500.00"))
        val credito = cuentasClient.acreditarCuenta("CUENTA_SESSION_DEST", BigDecimal("500.00"))

        assertAll({
            assertTrue(debito)
            assertTrue(credito)
        })
    }
}