package com.banco.transferencia.infrastructure.observability

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import java.util.concurrent.TimeUnit

class Metrics(
    private val registry: MeterRegistry
) {
    private val transferenciaIniciada = Counter.builder("transferencia.iniciada")
        .description("Número de transferencias iniciadas")
        .register(registry)
    
    private val transferenciaExitosa = Counter.builder("transferencia.exitosa")
        .description("Número de transferencias completadas exitosamente")
        .register(registry)
    
    private val transferenciaFallida = Counter.builder("transferencia.fallida")
        .description("Número de transferencias fallidas")
        .register(registry)
    
    private val transferenciaRechazada = Counter.builder("transferencia.rechazada")
        .description("Número de transferencias rechazadas por validación")
        .register(registry)
    
    private val duracionTransferencia = Timer.builder("transferencia.duracion")
        .description("Tiempo de procesamiento de transferencias")
        .publishPercentiles(0.5, 0.95, 0.99)
        .register(registry)
    
    private val montoTotal = Timer.builder("transferencia.monto")
        .description("Monto total transferido")
        .publishPercentiles(0.5, 0.95, 0.99)
        .register(registry)
    
    private val operacionesOrigen = Counter.builder("operaciones.cuenta")
        .tag("tipo", "origen")
        .description("Operaciones por cuenta origen")
        .register(registry)
    
    private val operacionesDestino = Counter.builder("operaciones.cuenta")
        .tag("tipo", "destino")
        .description("Operaciones por cuenta destino")
        .register(registry)
    
    private val idempotenciaHit = Counter.builder("idempotencia.hit")
        .description("Claves de idempotencia encontradas")
        .register(registry)
    
    private val idempotenciaMiss = Counter.builder("idempotencia.miss")
        .description("Claves de idempotencia no encontradas")
        .register(registry)
    
    private val validacionDuracion = Timer.builder("transferencia.validacion.duracion")
        .description("Tiempo de validación de transferencias")
        .register(registry)
    
    private val persistenciaDuracion = Timer.builder("transferencia.persistencia.duracion")
        .description("Tiempo de persistencia de transferencias")
        .register(registry)
    
    private val contadoresPersonalizados = mutableMapOf<String, Counter>()
    
    fun registrarTransferenciaIniciada() {
        transferenciaIniciada.increment()
    }
    
    fun registrarTransferenciaExitosa() {
        transferenciaExitosa.increment()
    }
    
    fun registrarTransferenciaFallida() {
        transferenciaFallida.increment()
    }
    
    fun registrarTransferenciaRechazada() {
        transferenciaRechazada.increment()
    }
    
    fun registrarDuracionTransferencia(duracionMillis: Long) {
        duracionTransferencia.record(duracionMillis, TimeUnit.MILLISECONDS)
    }
    
    fun registrarMonto(monto: Double) {
        montoTotal.record(monto)
    }
    
    fun registrarOperacionCuentaOrigen() {
        operacionesOrigen.increment()
    }
    
    fun registrarOperacionCuentaDestino() {
        operacionesDestino.increment()
    }
    
    fun registrarIdempotenciaHit() {
        idempotenciaHit.increment()
    }
    
    fun registrarIdempotenciaMiss() {
        idempotenciaMiss.increment()
    }
    
    fun registrarValidacionDuracion(duracionMillis: Long) {
        validacionDuracion.record(duracionMillis, TimeUnit.MILLISECONDS)
    }
    
    fun registrarPersistenciaDuracion(duracionMillis: Long) {
        persistenciaDuracion.record(duracionMillis, TimeUnit.MILLISECONDS)
    }
    
    fun incrementar(nombre: String, cantidad: Long = 1) {
        val counter = contadoresPersonalizados.getOrPut(nombre) {
            Counter.builder(nombre)
                .description("Contador dinámico: $nombre")
                .register(registry)
        }
        counter.increment(cantidad.toDouble())
    }
}