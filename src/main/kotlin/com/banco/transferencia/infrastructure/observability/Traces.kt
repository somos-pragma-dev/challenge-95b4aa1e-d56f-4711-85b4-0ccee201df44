package com.banco.transferencia.infrastructure.observability

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporterBuilder
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.logs.LogRecordProcessor
import io.opentelemetry.sdk.logs.SdkLogRecordProvider
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import io.opentelemetry.sdk.trace.export.SpanExporter
import io.opentelemetry.semconv.ResourceAttributes
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object Traces {
    private var tracer: Tracer? = null
    private var openTelemetry: OpenTelemetry? = null
    private val eventosPorTrace = ConcurrentHashMap<String, MutableList<String>>()
    
    fun inicializar(serviceName: String = "transferencia-service"): Tracer {
        val resource = Resource.getDefault().toBuilder()
            .put(ResourceAttributes.SERVICE_NAME, serviceName)
            .put(ResourceAttributes.SERVICE_VERSION, "1.0.0")
            .build()
        
        val tracerProvider = SdkTracerProvider.builder()
            .setResource(resource)
            .addSpanProcessor(createBatchSpanProcessor())
            .build()
        
        val logRecordProvider = SdkLogRecordProvider.builder()
            .setResource(resource)
            .addLogRecordProcessor(createBatchLogRecordProcessor())
            .build()
        
        openTelemetry = OpenTelemetrySdk.builder()
            .setTracerProvider(tracerProvider)
            .setLogRecordProvider(logRecordProvider)
            .setPropagators(
                ContextPropagators.create(W3CTraceContextPropagator.getInstance())
            )
            .build()
        
        tracer = openTelemetry!!.getTracer(serviceName)
        return tracer!!
    }
    
    private fun createBatchSpanProcessor(): BatchSpanProcessor {
        val exporter: SpanExporter = OtlpGrpcSpanExporter.builder()
            .setEndpoint("http://localhost:4317")
            .build()
        
        return BatchSpanProcessor.builder(exporter).build()
    }
    
    private fun createBatchLogRecordProcessor(): LogRecordProcessor {
        val exporter = OtlpGrpcLogRecordExporter.builder()
            .setEndpoint("http://localhost:4317")
            .build()
        
        return BatchLogRecordProcessor.builder(exporter).build()
    }
    
    fun obtenerTracer(): Tracer {
        return tracer ?: throw IllegalStateException("Traces no inicializado. Llame a Traces.inicializar() primero.")
    }
    
    fun obtenerOpenTelemetry(): OpenTelemetry {
        return openTelemetry ?: throw IllegalStateException("Traces no inicializado. Llame a Traces.inicializar() primero.")
    }
    
    fun generarTraceId(): String {
        return UUID.randomUUID().toString()
    }
    
    fun agregarEvento(evento: String, traceId: String? = null) {
        val id = traceId ?: "unknown"
        val eventos = eventosPorTrace.getOrPut(id) { mutableListOf() }
        synchronized(eventos) {
            eventos.add("$evento:${System.currentTimeMillis()}")
        }
    }
    
    fun obtenerEventos(traceId: String): List<String> {
        return eventosPorTrace[traceId]?.toList() ?: emptyList()
    }
    
    fun cerrar() {
        tracer?.let { (it as? io.opentelemetry.sdk.trace.SdkTracer)?.close() }
        openTelemetry?.close()
    }
}