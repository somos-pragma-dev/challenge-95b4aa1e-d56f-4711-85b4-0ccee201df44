package com.banco.transferencia.infrastructure.observability

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import ch.qos.logback.core.util.FileSize
import ch.qos.logback.core.util.OptionHelper
import net.logstash.logback.LogstashLayout
import net.logstash.logback.encoder.LogstashEncoder
import org.slf4j.LoggerFactory
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Logs {
    private const val RUTA_LOGS = "logs"
    private const val PATRON_FECHA = "yyyy-MM-dd"
    private const val MAX_ARCHIVOS = 30
    private const val TAMANO_MAXIMO = "100MB"
    
    private val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
    private val loggerRoot: Logger = loggerContext.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)
    
    fun inicializar(nivelPorDefecto: String = "INFO") {
        crearDirectorioLogs()
        configurarAppenderConsola()
        configurarAppenderArchivo()
        configurarNivelRaiz(nivelPorDefecto)
    }
    
    private fun crearDirectorioLogs() {
        val dir = File(RUTA_LOGS)
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }
    
    private fun configurarAppenderConsola() {
        val appender = crearAppenderConsola(loggerContext)
        appender.start()
        loggerRoot.addAppender(appender)
    }
    
    private fun crearAppenderConsola(context: LoggerContext): Appender<ILoggingEvent> {
        val encoder = LogstashEncoder()
        encoder.context = context
        encoder.start()
        
        val appender = ch.qos.logback.core.ConsoleAppender<ILoggingEvent>()
        appender.name = "CONSOLE"
        appender.context = context
        appender.encoder = encoder
        return appender
    }
    
    private fun configurarAppenderArchivo() {
        val appender = crearAppenderArchivo(loggerContext)
        appender.start()
        loggerRoot.addAppender(appender)
    }
    
    private fun crearAppenderArchivo(context: LoggerContext): Appender<ILoggingEvent> {
        val layout = crearLayoutJSON(context)
        
        val appender = RollingFileAppender<ILoggingEvent>()
        appender.name = "FILE"
        appender.context = context
        appender.file = "$RUTA_LOGS/app.log"
        appender.encoder = LogstashEncoder().apply {
            this.layout = layout
            context = context
        }
        
        val policy = TimeBasedRollingPolicy<ILoggingEvent>()
        policy.context = context
        policy.fileNamePattern = "$RUTA_LOGS/app-%d{yyyy-MM-dd}.%i.log.gz"
        policy.maxHistory = MAX_ARCHIVOS
        policy.setTotalSizeCap(FileSize.valueOf(TAMANO_MAXIMO))
        policy.timeBasedFileNamingAndTriggeringPolicy = 
            ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP<ILoggingEvent>().apply {
                maxFileSize = FileSize.valueOf(TAMANO_MAXIMO)
            }
        policy.parent = appender
        policy.start()
        
        appender.rollingPolicy = policy
        return appender
    }
    
    private fun crearLayoutJSON(context: LoggerContext): LogstashLayout {
        return LogstashLayout.Builder()
            .context(context)
            .customFields("{\"app\":\"transferencia-service\"}")
            .includeMdcKeyName("correlationId")
            .includeMdcKeyName("userId")
            .includeMdcKeyName("traceId")
            .build()
    }
    
    private fun configurarNivelRaiz(nivel: String) {
        loggerRoot.level = Level.toLevel(nivel)
    }
    
    fun registrar(mensaje: String, contexto: Map<String, Any> = emptyMap()) {
        val logger = loggerContext.getLogger("com.banco.transferencia")
        
        val mdc = contexto.mapKeys { it.key }
        val evento = buildString {
            append(mensaje)
            if (contexto.isNotEmpty()) {
                append(" | ")
                append(contexto.entries.joinToString(", ") { "${it.key}=${it.value}" })
            }
        }
        
        when {
            contexto.containsKey("error") -> logger.error(evento)
            else -> logger.info(evento)
        }
    }
    
    fun obtenerLogger(clase: Class<*>): Logger {
        return loggerContext.getLogger(clase)
    }
}