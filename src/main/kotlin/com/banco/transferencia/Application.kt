package com.banco.transferencia

import com.banco.transferencia.domain.TransferenciaRepository
import com.banco.transferencia.infrastructure.TransferenciaController
import com.banco.transferencia.infrastructure.config.ResilienceConfig
import com.banco.transferencia.infrastructure.observability.Logs
import com.banco.transferencia.infrastructure.observability.Metrics
import com.banco.transferencia.infrastructure.observability.Traces
import com.banco.transferencia.infrastructure.persistence.TransferenciaRepositoryImpl
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.insertkoin.ktor.plugin.KoinPlugin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.slf4j.LoggerFactory

fun main() {
    val logger = LoggerFactory.getLogger("Application")
    
    withContext(Dispatchers.IO) {
        logger.info("Iniciando microservicio de transferencias bancarias")
        
        val server = embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
            installKoin()
            installContentNegotiation()
            installStatusPages()
            installObservability()
            configureRouting()
        }
        
        server.start(wait = true)
    }
}

private fun Application.installKoin() {
    install(Koin) {
        modules(
            module {
                single<TransferenciaRepository> { TransferenciaRepositoryImpl() }
                single { ResilienceConfig.buildCircuitBreaker() }
                single { Metrics.buildRegistry() }
                single { Traces.getTracer() }
                single { Logs.getLogger() }
            }
        )
    }
}

private fun Application.installContentNegotiation() {
    install(ContentNegotiation) {
        json(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }
}

private fun Application.installStatusPages() {
    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to cause.message))
        }
        exception<IllegalStateException> { call, cause ->
            call.respond(HttpStatusCode.Conflict, mapOf("error" to cause.message))
        }
        exception<Exception> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error interno del servidor"))
        }
    }
}

private fun Application.installObservability() {
    val logger = Logs.getLogger()
    val metrics = Metrics.buildRegistry()
    val tracer = Traces.getTracer()
    
    logger.info("Observabilidad inicializada - Métricas: ${metrics.counter("app.started").count()}")
}

private fun Application.configureRouting() {
    val controller = TransferenciaController(get(), get(), get(), get(), get(), get())
    controller.registerRoutes(this)
}