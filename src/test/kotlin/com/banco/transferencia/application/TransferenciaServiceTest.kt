package com.banco.transferencia.application

import com.banco.transferencia.domain.EstadoTransferencia
import com.banco.transferencia.domain.Transferencia
import com.banco.transferencia.domain.TransferenciaRepository
import com.banco.transferencia.domain.TransferenciaRequest
import com.banco.transferencia.infrastructure.clients.CuentasClient
import com.banco.transferencia.infrastructure.clients.NotificacionClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import org.jetbrains.kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertEquals
import org.junit.jupiter.api.assertFalse
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.assertTrue
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.Instant
import kotlin.test.assertNotEquals

class TransferenciaServiceTest {

    private lateinit var repository: TransferenciaRepository
    private lateinit var cuentasClient: CuentasClient
    private lateinit var notificacionClient: NotificacionClient
    private lateinit var httpClient: HttpClient
    private lateinit var service: TransferenciaService

    @BeforeEach
    fun setUp() {
        repository = mock()
        cuentasClient = mock()
        notificacionClient = mock()
        httpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
        service = TransferenciaService(repository, cuentasClient, notificacionClient, httpClient)
    }

    @Test
    fun `procesarTransferencia exitosa retorna transferencia completada`() = runTest {
        val request = TransferenciaRequest(
            cuentaOrigen = "CUENTA_ORIGEN_001",
            cuentaDestino = "CUENTA_DESTINO_001",
            monto = BigDecimal("1000.00"),
            descripcion = "Transferencia de prueba"
        )
        val transferenciaGuardada = Transferencia(
            id = "TXN_001",
            cuentaOrigen = request.cuentaOrigen,
            cuentaDestino = request.cuentaDestino,
            monto = request.monto,
            descripcion = request.descripcion,
            estado = EstadoTransferencia.PENDIENTE,
            claveIdempotencia = "idem_123",
            createdAt = Instant.now()
        )
        whenever(repository.buscarPorClaveIdempotencia(any())).thenReturn(null)
        whenever(repository.guardar(any())).thenReturn(transferenciaGuardada)
        whenever(cuentasClient.debitarCuenta(any(), any())).thenReturn(true)
        whenever(cuentasClient.acreditarCuenta(any(), any())).thenReturn(true)

        val resultado = service.procesarTransferencia(request, "idem_123")

        assertTrue(resultado.isSuccess)
        val transferencia = resultado.getOrNull()
        assertNotNull(transferencia)
        assertEquals(EstadoTransferencia.COMPLETADA, transferencia.estado)
    }

    @Test
    fun `procesarTransferencia con clave idempotencia existente retorna la transferencia original`() = runTest {
        val claveIdempotencia = "idem_existente_456"
        val transferenciaExistente = Transferencia(
            id = "TXN_EXISTENTE",
            cuentaOrigen = "CUENTA_A",
            cuentaDestino = "CUENTA_B",
            monto = BigDecimal("500.00"),
            descripcion = "Transferencia existente",
            estado = EstadoTransferencia.COMPLETADA,
            claveIdempotencia = claveIdempotencia,
            createdAt = Instant.now()
        )
        whenever(repository.buscarPorClaveIdempotencia(claveIdempotencia)).thenReturn(transferenciaExistente)

        val request = TransferenciaRequest(
            cuentaOrigen = "CUENTA_A",
            cuentaDestino = "CUENTA_B",
            monto = BigDecimal("500.00"),
            descripcion = "Transferencia existente"
        )

        val resultado = service.procesarTransferencia(request, claveIdempotencia)

        assertTrue(resultado.isSuccess)
        val transferencia = resultado.getOrNull()
        assertNotNull(transferencia)
        assertEquals("TXN_EXISTENTE", transferencia.id)
        verify(repository, never()).guardar(any())
    }

    @Test
    fun `procesarTransferencia con monto negativo falla validacion`() = runTest {
        val request = TransferenciaRequest(
            cuentaOrigen = "CUENTA_ORIGEN_001",
            cuentaDestino = "CUENTA_DESTINO_001",
            monto = BigDecimal("-100.00"),
            descripcion = "Monto negativo"
        )

        val resultado = service.procesarTransferencia(request, "idem_789")

        assertTrue(resultado.isFailure)
        assertTrue(resultado.exceptionOrNull()?.message?.contains("monto") == true)
    }

    @Test
    fun `procesarTransferencia con cuentas iguales falla validacion`() = runTest {
        val request = TransferenciaRequest(
            cuentaOrigen = "CUENTA_IGUAL",
            cuentaDestino = "CUENTA_IGUAL",
            monto = BigDecimal("100.00"),
            descripcion = "Cuentas iguales"
        )

        val resultado = service.procesarTransferencia(request, "idem_101")

        assertTrue(resultado.isFailure)
    }

    @Test
    fun `procesarTransferencia cuando debito falla marca como fallida`() = runTest {
        val request = TransferenciaRequest(
            cuentaOrigen = "CUENTA_ORIGEN_002",
            cuentaDestino = "CUENTA_DESTINO_002",
            monto = BigDecimal("2000.00"),
            descripcion = "Transferencia que fallara"
        )
        val transferenciaGuardada = Transferencia(
            id = "TXN_002",
            cuentaOrigen = request.cuentaOrigen,
            cuentaDestino = request.cuentaDestino,
            monto = request.monto,
            descripcion = request.descripcion,
            estado = EstadoTransferencia.PENDIENTE,
            claveIdempotencia = "idem_debito_fallo",
            createdAt = Instant.now()
        )
        whenever(repository.buscarPorClaveIdempotencia(any())).thenReturn(null)
        whenever(repository.guardar(any())).thenReturn(transferenciaGuardada)
        whenever(cuentasClient.debitarCuenta(any(), any())).thenReturn(false)

        val resultado = service.procesarTransferencia(request, "idem_debito_fallo")

        assertTrue(resultado.isSuccess)
        val transferencia = resultado.getOrNull()
        assertNotNull(transferencia)
        assertEquals(EstadoTransferencia.FALLIDA, transferencia.estado)
    }

    @Test
    fun `buscarTransferenciaPorId existente retorna transferencia`() = runTest {
        val transferenciaEsperada = Transferencia(
            id = "TXN_BUSCAR",
            cuentaOrigen = "CUENTA_A",
            cuentaDestino = "CUENTA_B",
            monto = BigDecimal("300.00"),
            descripcion = "Buscar por ID",
            estado = EstadoTransferencia.COMPLETADA,
            claveIdempotencia = "idem_buscar",
            createdAt = Instant.now()
        )
        whenever(repository.buscarPorId("TXN_BUSCAR")).thenReturn(transferenciaEsperada)

        val resultado = service.buscarTransferenciaPorId("TXN_BUSCAR")

        assertTrue(resultado.isSuccess)
        assertEquals(transferenciaEsperada, resultado.getOrNull())
    }

    @Test
    fun `buscarTransferenciaPorId inexistente retorna failure`() = runTest {
        whenever(repository.buscarPorId("TXN_INEXISTENTE")).thenReturn(null)

        val resultado = service.buscarTransferenciaPorId("TXN_INEXISTENTE")

        assertTrue(resultado.isFailure)
    }

    @Test
    fun `listarTransferenciasPorCuentaOrigen retorna lista de transferencias`() = runTest {
        val transferencias = listOf(
            Transferencia(
                id = "TXN_1",
                cuentaOrigen = "CUENTA_ORIGEN_LISTA",
                cuentaDestino = "CUENTA_B",
                monto = BigDecimal("100.00"),
                descripcion = "Transferencia 1",
                estado = EstadoTransferencia.COMPLETADA,
                claveIdempotencia = "idem_1",
                createdAt = Instant.now()
            ),
            Transferencia(
                id = "TXN_2",
                cuentaOrigen = "CUENTA_ORIGEN_LISTA",
                cuentaDestino = "CUENTA_C",
                monto = BigDecimal("200.00"),
                descripcion = "Transferencia 2",
                estado = EstadoTransferencia.COMPLETADA,
                claveIdempotencia = "idem_2",
                createdAt = Instant.now()
            )
        )
        whenever(repository.listarPorCuentaOrigen("CUENTA_ORIGEN_LISTA")).thenReturn(transferencias)

        val resultado = service.listarTransferenciasPorCuentaOrigen("CUENTA_ORIGEN_LISTA")

        assertTrue(resultado.isSuccess)
        assertEquals(2, resultado.getOrNull()?.size)
    }

    @Test
    fun `validar transferencia con monto cero falla`() {
        val transferencia = Transferencia(
            id = "TXN_CERO",
            cuentaOrigen = "CUENTA_A",
            cuentaDestino = "CUENTA_B",
            monto = BigDecimal.ZERO,
            descripcion = "Monto cero",
            estado = EstadoTransferencia.PENDIENTE,
            claveIdempotencia = "idem_cero",
            createdAt = Instant.now()
        )

        val resultado = transferencia.validar()

        assertTrue(resultado.isFailure)
    }

    @Test
    fun `validar transferencia con cuenta origen vacia falla`() {
        val transferencia = Transferencia(
            id = "TXN_ORIGEN_VACIO",
            cuentaOrigen = "",
            cuentaDestino = "CUENTA_B",
            monto = BigDecimal("100.00"),
            descripcion = "Origen vacio",
            estado = EstadoTransferencia.PENDIENTE,
            claveIdempotencia = "idem_origen_vacio",
            createdAt = Instant.now()
        )

        val resultado = transferencia.validar()

        assertTrue(resultado.isFailure)
    }

    @Test
    fun `cambiarEstado actualiza correctamente el estado de la transferencia`() {
        val transferencia = Transferencia(
            id = "TXN_CAMBIO_ESTADO",
            cuentaOrigen = "CUENTA_A",
            cuentaDestino = "CUENTA_B",
            monto = BigDecimal("150.00"),
            descripcion = "Cambio de estado",
            estado = EstadoTransferencia.PENDIENTE,
            claveIdempotencia = "idem_cambio",
            createdAt = Instant.now()
        )

        val transferenciaActualizada = transferencia.cambiarEstado(EstadoTransferencia.COMPLETADA, "Procesamiento exitoso")

        assertEquals(EstadoTransferencia.COMPLETADA, transferenciaActualizada.estado)
        assertEquals("Procesamiento exitoso", transferenciaActualizada.motivoRechazo)
    }

    @Test
    fun `puedeSerProcesada retorna true solo para transferencias pendientes`() {
        val transferenciaPendiente = Transferencia(
            id = "TXN_PEND",
            cuentaOrigen = "CUENTA_A",
            cuentaDestino = "CUENTA_B",
            monto = BigDecimal("100.00"),
            descripcion = "Pendiente",
            estado = EstadoTransferencia.PENDIENTE,
            claveIdempotencia = "idem_pend",
            createdAt = Instant.now()
        )
        val transferenciaCompletada = transferenciaPendiente.cambiarEstado(EstadoTransferencia.COMPLETADA)

        assertTrue(transferenciaPendiente.puedeSerProcesada())
        assertFalse(transferenciaCompletada.puedeSerProcesada())
    }

    @Test
    fun `conIdempotencia genera nueva clave idempotencia`() {
        val transferencia = Transferencia(
            id = "TXN_IDEM",
            cuentaOrigen = "CUENTA_A",
            cuentaDestino = "CUENTA_B",
            monto = BigDecimal("100.00"),
            descripcion = "Idempotencia",
            estado = EstadoTransferencia.PENDIENTE,
            claveIdempotencia = "idem_original",
            createdAt = Instant.now()
        )

        val nuevaTransferencia = transferencia.conIdempotencia("idem_nueva")

        assertEquals("idem_nueva", nuevaTransferencia.claveIdempotencia)
        assertNotEquals(transferencia, nuevaTransferencia)
    }

    @Test
    fun `aDominio convierte request a dominio correctamente`() {
        val request = TransferenciaRequest(
            cuentaOrigen = "CUENTA_ORIGEN_DOMINIO",
            cuentaDestino = "CUENTA_DESTINO_DOMINIO",
            monto = BigDecimal("500.00"),
            descripcion = "Conversion a dominio"
        )

        val transferencia = request.aDominio()

        assertEquals(request.cuentaOrigen, transferencia.cuentaOrigen)
        assertEquals(request.cuentaDestino, transferencia.cuentaDestino)
        assertEquals(request.monto, transferencia.monto)
        assertEquals(request.descripcion, transferencia.descripcion)
        assertEquals(EstadoTransferencia.PENDIENTE, transferencia.estado)
    }

    @Test
    fun `notificacion se envia al completar transferencia exitosamente`() = runTest {
        val request = TransferenciaRequest(
            cuentaOrigen = "CUENTA_NOTIF",
            cuentaDestino = "CUENTA_DEST_NOTIF",
            monto = BigDecimal("750.00"),
            descripcion = "Transferencia con notificacion"
        )
        val transferenciaGuardada = Transferencia(
            id = "TXN_NOTIF",
            cuentaOrigen = request.cuentaOrigen,
            cuentaDestino = request.cuentaDestino,
            monto = request.monto,
            descripcion = request.descripcion,
            estado = EstadoTransferencia.PENDIENTE,
            claveIdempotencia = "idem_notif",
            createdAt = Instant.now()
        )
        whenever(repository.buscarPorClaveIdempotencia(any())).thenReturn(null)
        whenever(repository.guardar(any())).thenReturn(transferenciaGuardada)
        whenever(cuentasClient.debitarCuenta(any(), any())).thenReturn(true)
        whenever(cuentasClient.acreditarCuenta(any(), any())).thenReturn(true)

        service.procesarTransferencia(request, "idem_notif")

        verify(notificacionClient).enviarNotificacion(any())
    }

    @Test
    fun `listarTransferenciasPorRangoDeTiempo retorna transferencias en el rango`() = runTest {
        val desde = Instant.parse("2024-01-01T00:00:00Z")
        val hasta = Instant.parse("2024-01-31T23:59:59Z")
        val transferencias = listOf(
            Transferencia(
                id = "TXN_RANGO_1",
                cuentaOrigen = "CUENTA_A",
                cuentaDestino = "CUENTA_B",
                monto = BigDecimal("100.00"),
                descripcion = "Rango 1",
                estado = EstadoTransferencia.COMPLETADA,
                claveIdempotencia = "idem_r1",
                createdAt = Instant.parse("2024-01-15T10:00:00Z")
            )
        )
        whenever(repository.listarPorRangoDeTiempo(desde, hasta)).thenReturn(transferencias)

        val resultado = service.listarTransferenciasPorRangoDeTiempo(desde, hasta)

        assertTrue(resultado.isSuccess)
        assertEquals(1, resultado.getOrNull()?.size)
    }

    @Test
    fun `contarPorEstado retorna cantidad correcta de transferencias`() = runTest {
        whenever(repository.contarPorEstado(EstadoTransferencia.COMPLETADA)).thenReturn(15)

        val resultado = service.contarTransferenciasPorEstado(EstadoTransferencia.COMPLETADA)

        assertTrue(resultado.isSuccess)
        assertEquals(15, resultado.getOrNull())
    }
}