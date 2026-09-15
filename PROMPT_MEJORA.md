# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Boilerplate del stack que falta

Sin esto no compila ni arranca. Es andamiaje, no toca nada de lo pedagogico:

- **Manifiesto de dependencias del stack elegido** — Sin un manifiesto de dependencias reconocible, ninguna herramienta de build sabe que instalar y el proyecto no arranca.
- **Punto de entrada del stack elegido** — Sin un punto de entrada reconocible, el runtime no tiene por donde arrancar la aplicacion.
- **Capa de interfaz (controller/handler)** — Sin una capa de interfaz explicita, no hay forma de invocar la logica de negocio desde afuera del proceso.

## Como saber que terminaste

```bash
el comando de build o arranque canonico del stack elegido
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Contexto técnico original
Sistema distribuido con Kotlin, Ktor, circuit breakers y observabilidad

### Reto
- Tema: microservicios
- Seniority: junior-l2
- Tipo: practical
- Título: Implementación de un microservicio en un sistema distribuido
- Tiempo estimado: 20 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Definición del microservicio — objetivo: Establecer los límites y responsabilidades del microservicio de transferencias. — entregable (NO resolver): Documento que describe el microservicio, sus responsabilidades y las reglas de negocio.
- Fase 2: Implementación de la idempotencia — objetivo: Asegurar que las solicitudes de transferencia sean idempotentes. — entregable (NO resolver): Mecanismo de idempotencia implementado y documentado.
- Fase 3: Manejo de la consistencia eventual — objetivo: Implementar estrategias para manejar la consistencia eventual entre servicios. — entregable (NO resolver): Estrategias de consistencia eventual implementadas y documentadas.
- Fase 4: Implementación de observabilidad — objetivo: Proveer observabilidad para el microservicio mediante métricas, trazas y logs. — entregable (NO resolver): Sistema de observabilidad implementado y documentado.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: build.gradle.kts ===
plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    application
}

group = "com.banco.transferencia"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.3.12")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.12")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.12")
    implementation("io.ktor:ktor-client-core-jvm:2.3.12")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.12")
    implementation("io.github.resilience4j:resilience4j-ktor:2.1.0")
    implementation("io.micrometer:micrometer-core:1.13.0")
    implementation("io.opentelemetry:opentelemetry-api:1.36.0")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("org.jetbrains.exposed:exposed-core:0.46.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("io.insert-koin:ktor-server:3.5.3")
    
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.22")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.35.2")
    testImplementation("io.ktor:ktor-server-test-host:2.3.12")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.banco.transferencia.ApplicationKt")
}

tasks.test {
    useJUnit()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/Application.kt ===
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

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/domain/Transferencia.kt ===
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

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/domain/TransferenciaRepository.kt ===
package com.banco.transferencia.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

/**
 * Interfaz del repositorio de transferencias que define las operaciones
 * de persistencia y consulta para el modelo de dominio Transferencia.
 * 
 * Esta interfaz sigue el patrón Repository de Domain-Driven Design,
 * encapsulando la lógica de acceso a datos detrás de abstracciones
 * del dominio. La implementación concreta será provista por la capa
 * de infraestructura.
 * 
 * Las operaciones son suspendidas para soportar el modelo reactivo
 * del microservicio, permitiendo procesamiento asíncrono sin bloquear
 * el hilo de ejecución.
 */
interface TransferenciaRepository {
    
    /**
     * Persiste una nueva transferencia en el repositorio.
     * @param transferencia La transferencia a guardar
     * @return La transferencia persistida con su ID asignado
     */
    suspend fun guardar(transferencia: Transferencia): Transferencia
    
    /**
     * Actualiza una transferencia existente en el repositorio.
     * @param transferencia La transferencia con los datos actualizados
     * @return La transferencia actualizada
     */
    suspend fun actualizar(transferencia: Transferencia): Transferencia
    
    /**
     * Busca una transferencia por su identificador único.
     * @param id El identificador de la transferencia
     * @return La transferencia encontrada o null si no existe
     */
    suspend fun buscarPorId(id: String): Transferencia?
    
    /**
     * Busca una transferencia por su clave de idempotencia.
     * Este método es crucial para implementar la idempotencia,
     * permitiendo detectar y evitar procesamientos duplicados.
     * @param kunci La clave de idempotencia
     * @return La transferencia encontrada o null si no existe
     */
    suspend fun buscarPorClaveIdempotencia(clave: String): Transferencia?
    
    /**
     * Lista todas las transferencias de una cuenta origen.
     * @param cuentaOrigen La cuenta de origen
     * @return Lista de transferencias realizadas desde esa cuenta
     */
    suspend fun listarPorCuentaOrigen(cuentaOrigen: String): List<Transferencia>
    
    /**
     * Lista todas las transferencias de una cuenta destino.
     * @param cuentaDestino La cuenta de destino
     * @return Lista de transferencias recibidas en esa cuenta
     */
    suspend fun listarPorCuentaDestino(cuentaDestino: String): List<Transferencia>
    
    /**
     * Lista todas las transferencias en un rango de tiempo.
     * Útil para auditoría y reportes.
     * @param desde Fecha de inicio del rango
     * @param hasta Fecha de fin del rango
     * @return Lista de transferencias en el rango especificado
     */
    suspend fun listarPorRangoDeTiempo(desde: Instant, hasta: Instant): List<Transferencia>
    
    /**
     * Cuenta el número de transferencias en un estado específico.
     * @param estado El estado de las transferencias a contar
     * @return Cantidad de transferencias en el estado especificado
     */
    suspend fun contarPorEstado(estado: EstadoTransferencia): Int
}

/**
 * Implementación en memoria del repositorio de transferencias.
 * Esta implementación es útil para desarrollo y testing, pero en
 * producción debe reemplazarse por una implementación que persista
 * en una base de datos real.
 * 
 * Utiliza un ConcurrentHashMap para almacenar las transferencias,
 * proporcionando seguridad en entornos concurrentes.
 */
class TransferenciaRepositoryInMemory : TransferenciaRepository {
    
    private val almacenamiento = ConcurrentHashMap<String, Transferencia>()
    private val indiceIdempotencia = ConcurrentHashMap<String, String>()
    private val indiceCuentaOrigen = ConcurrentHashMap<String, MutableList<String>>()
    private val indiceCuentaDestino = ConcurrentHashMap<String, MutableList<String>>()
    
    override suspend fun guardar(transferencia: Transferencia): Transferencia {
        return withContext(Dispatchers.IO) {
            almacenamiento[transferencia.id] = transferencia
            
            transferencia.kunci Idempotencia?.let { clave ->
                indiceIdempotencia[clave] = transferencia.id
            }
            
            indiceCuentaOrigen.getOrPut(transferencia.cuentaOrigen) { mutableListOf() }
                .add(transferencia.id)
            
            indiceCuentaDestino.getOrPut(transferencia.cuentaDestino) { mutableListOf() }
                .add(transferencia.id)
            
            transferencia
        }
    }
    
    override suspend fun actualizar(transferencia: Transferencia): Transferencia {
        return withContext(Dispatchers.IO) {
            require(almacenamiento.containsKey(transferencia.id)) {
                "Transferencia no encontrada: ${transferencia.id}"
            }
            almacenamiento[transferencia.id] = transferencia
            transferencia
        }
    }
    
    override suspend fun buscarPorId(id: String): Transferencia? {
        return withContext(Dispatchers.IO) {
            almacenamiento[id]
        }
    }
    
    override suspend fun buscarPorClaveIdempotencia(clave: String): Transferencia? {
        return withContext(Dispatchers.IO) {
            val id = indiceIdempotencia[clave] ?: return@withContext null
            almacenamiento[id]
        }
    }
    
    override suspend fun listarPorCuentaOrigen(cuentaOrigen: String): List<Transferencia> {
        return withContext(Dispatchers.IO) {
            val ids = indiceCuentaOrigen[cuentaOrigen] ?: emptyList()
            ids.mapNotNull { almacenamiento[it] }
        }
    }
    
    override suspend fun listarPorCuentaDestino(cuentaDestino: String): List<Transferencia> {
        return withContext(Dispatchers.IO) {
            val ids = indiceCuentaDestino[cuentaDestino] ?: emptyList()
            ids.mapNotNull { almacenamiento[it] }
        }
    }
    
    override suspend fun listarPorRangoDeTiempo(desde: Instant, hasta: Instant): List<Transferencia> {
        return withContext(Dispatchers.IO) {
            almacenamiento.values.filter { t ->
                !t.timestampCreacion.isBefore(desde) && !t.timestampCreacion.isAfter(hasta)
            }
        }
    }
    
    override suspend fun contarPorEstado(estado: EstadoTransferencia): Int {
        return withContext(Dispatchers.IO) {
            almacenamiento.values.count { it.estado == estado }
        }
    }
}


// === ARCHIVO: src/main/kotlin/com/banco/transferencia/application/TransferenciaService.kt ===
package com.banco.transferencia.application

import com.banco.transferencia.domain.Transferencia
import com.banco.transferencia.domain.TransferenciaRepository
import com.banco.transferencia.domain.TransferenciaRequest
import com.banco.transferencia.domain.TransferenciaResponse
import com.banco.transferencia.domain.EstadoTransferencia
import com.banco.transferencia.infrastructure.clients.CuentasClient
import com.banco.transferencia.infrastructure.clients.NotificacionClient
import com.banco.transferencia.infrastructure.observability.Metrics
import com.banco.transferencia.infrastructure.observability.Traces
import com.banco.transferencia.infrastructure.observability.Logs
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class TransferenciaService(
    private val repository: TransferenciaRepository,
    private val cuentasClient: CuentasClient,
    private val notificacionClient: NotificacionClient,
    private val httpClient: HttpClient,
    private val metrics: Metrics,
    private val traces: Traces,
    private val logs: Logs
) {
    suspend fun crearTransferencia(request: TransferenciaRequest, claveIdempotencia: String?): Result<TransferenciaResponse> = withContext(Dispatchers.IO) {
        val traceId = traces.generarTraceId()
        logs.registrar("Iniciando transferencia", mapOf(
            "traceId" to traceId,
            "cuentaOrigen" to request.cuentaOrigen,
            "cuentaDestino" to request.cuentaDestino,
            "monto" to request.monto.toString(),
            "claveIdempotencia" to (claveIdempotencia ?: "sin_clave")
        ))
        
        try {
            claveIdempotencia?.let { clave ->
                val transferenciaExistente = repository.buscarPorClaveIdempotencia(clave)
                if (transferenciaExistente != null) {
                    logs.registrar("Transferencia idempotente encontrada", mapOf(
                        "traceId" to traceId,
                        "transferenciaId" to transferenciaExistente.id
                    ))
                    metrics.incrementar("transferencia.idempotente.hit")
                    return@withContext Result.success(transferenciaExistente.aRespuesta())
                }
            }
            
            val transferencia = request.aDominio().conIdempotencia(claveIdempotencia ?: "${request.cuentaOrigen}-${Instant.now()}")
            val validacion = transferencia.validar()
            
            if (validacion.isFailure) {
                logs.registrar("Validacion de transferencia fallida", mapOf(
                    "traceId" to traceId,
                    "error" to validacion.exceptionOrNull()?.message ?: "error desconocido"
                ))
                metrics.incrementar("transferencia.validacion.fallida")
                return@withContext Result.failure(validacion.exceptionOrNull()!!)
            }
            
            val transferenciaValidada = validacion.getOrThrow()
            val transferenciaGuardada = repository.guardar(transferenciaValidada)
            
            metrics.incrementar("transferencia.creada")
            traces.agregarEvento("transferencia.guardada", traceId)
            
            val resultadoDebito = cuentasClient.debitarCuenta(
                cuenta = transferenciaGuardada.cuentaOrigen,
                monto = transferenciaGuardada.monto,
                referencia = transferenciaGuardada.id
            )
            
            if (resultadoDebito.isFailure) {
                val transferenciaFallida = transferenciaGuardada.cambiarEstado(
                    EstadoTransferencia.FALLIDA,
                    "Error al debitar cuenta origen: ${resultadoDebito.exceptionOrNull()?.message}"
                )
                repository.actualizar(transferenciaFallida)
                logs.registrar("Debito fallido", mapOf(
                    "traceId" to traceId,
                    "error" to resultadoDebito.exceptionOrNull()?.message ?: "error desconocido"
                ))
                metrics.incrementar("transferencia.debito.fallido")
                return@withContext Result.failure(resultadoDebito.exceptionOrNull()!!)
            }
            
            val resultadoCredito = cuentasClient.creditarCuenta(
                cuenta = transferenciaGuardada.cuentaDestino,
                monto = transferenciaGuardada.monto,
                referencia = transferenciaGuardada.id
            )
            
            if (resultadoCredito.isFailure) {
                val compensacion = cuentasClient.creditarCuenta(
                    cuenta = transferenciaGuardada.cuentaOrigen,
                    monto = transferenciaGuardada.monto,
                    referencia = "COMPENSACION-${transferenciaGuardada.id}"
                )
                
                val transferenciaFallida = transferenciaGuardada.cambiarEstado(
                    EstadoTransferencia.FALLIDA,
                    "Error al creditar cuenta destino - compensacion aplicada: ${compensacion.isSuccess}"
                )
                repository.actualizar(transferenciaFallida)
                logs.registrar("Credito fallido con compensacion", mapOf(
                    "traceId" to traceId,
                    "compensacionExitosa" to compensacion.isSuccess.toString()
                ))
                metrics.incrementar("transferencia.credito.fallido.compensado")
                return@withContext Result.failure(resultadoCredito.exceptionOrNull()!!)
            }
            
            val transferenciaCompletada = transferenciaGuardada.cambiarEstado(
                EstadoTransferencia.COMPLETADA,
                "Transferencia procesada exitosamente"
            )
            repository.actualizar(transferenciaCompletada)
            
            notificacionClient.enviarNotificacion(
                cuenta = transferenciaCompletada.cuentaOrigen,
                mensaje = "Se ha realizado una transferencia por ${transferenciaCompletada.monto} a la cuenta ${transferenciaCompletada.cuentaDestino}",
                tipo = "DEBITO"
            )
            
            notificacionClient.enviarNotificacion(
                cuenta = transferenciaCompletada.cuentaDestino,
                mensaje = "Se ha recibido una transferencia por ${transferenciaCompletada.monto} de la cuenta ${transferenciaCompletada.cuentaOrigen}",
                tipo = "CREDITO"
            )
            
            logs.registrar("Transferencia completada exitosamente", mapOf(
                "traceId" to traceId,
                "transferenciaId" to transferenciaCompletada.id
            ))
            metrics.incrementar("transferencia.completada")
            
            Result.success(transferenciaCompletada.aRespuesta())
        } catch (e: Exception) {
            logs.registrar("Error inesperado en transferencia", mapOf(
                "traceId" to traceId,
                "error" to e.message,
                "stackTrace" to e.stackTraceToString()
            ))
            metrics.incrementar("transferencia.error")
            Result.failure(e)
        }
    }
    
    suspend fun consultarTransferencia(id: String): Transferencia? = withContext(Dispatchers.IO) {
        logs.registrar("Consultando transferencia", mapOf("transferenciaId" to id))
        val transferencia = repository.buscarPorId(id)
        if (transferencia != null) {
            metrics.incrementar("transferencia.consultada")
        }
        transferencia
    }
    
    suspend fun listarTransferenciasPorOrigen(cuentaOrigen: String): List<Transferencia> = withContext(Dispatchers.IO) {
        logs.registrar("Listando transferencias por cuenta origen", mapOf("cuentaOrigen" to cuentaOrigen))
        repository.listarPorCuentaOrigen(cuentaOrigen)
    }
    
    suspend fun listarTransferenciasPorDestino(cuentaDestino: String): List<Transferencia> = withContext(Dispatchers.IO) {
        logs.registrar("Listando transferencias por cuenta destino", mapOf("cuentaDestino" to cuentaDestino))
        repository.listarPorCuentaDestino(cuentaDestino)
    }
    
    suspend fun listarTransferenciasPorTiempo(desde: Instant, hasta: Instant): List<Transferencia> = withContext(Dispatchers.IO) {
        logs.registrar("Listando transferencias por tiempo", mapOf(
            "desde" to desde.toString(),
            "hasta" to hasta.toString()
        ))
        repository.listarPorRangoDeTiempo(desde, hasta)
    }
    
    private fun Transferencia.aRespuesta(): TransferenciaResponse {
        return TransferenciaResponse(
            id = this.id,
            cuentaOrigen = this.cuentaOrigen,
            cuentaDestino = this.cuentaDestino,
            monto = this.monto,
            estado = this.estado.name,
            fechaCreacion = this.fechaCreacion,
            fechaActualizacion = this.fechaActualizacion,
            mensaje = "Transferencia procesada exitosamente"
        )
    }
}

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/clients/CuentasClient.kt ===
package com.banco.transferencia.infrastructure.clients

import com.banco.transferencia.infrastructure.observability.Metrics
import com.banco.transferencia.infrastructure.observability.Traces
import com.banco.transferencia.infrastructure.observability.Logs
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.math.BigDecimal

data class CuentaDto(
    val numeroCuenta: String,
    val saldo: BigDecimal,
    val tipoCuenta: String,
    val titular: String
)

data class MovimientoDto(
    val cuenta: String,
    val monto: BigDecimal,
    val tipoMovimiento: String,
    val referencia: String,
    val fecha: String
)

data class ResultadoOperacionCuenta(
    val exitosa: Boolean,
    val mensaje: String,
    val nuevoSaldo: BigDecimal? = null,
    val codigoOperacion: String? = null
)

class CuentasClient(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val metrics: Metrics,
    private val traces: Traces,
    private val logs: Logs
) {
    suspend fun debitarCuenta(cuenta: String, monto: BigDecimal, referencia: String): Result<ResultadoOperacionCuenta> = withContext(Dispatchers.IO) {
        val traceId = traces.generarTraceId()
        logs.registrar("Iniciando debito en cuenta", mapOf(
            "traceId" to traceId,
            "cuenta" to cuenta,
            "monto" to monto.toString(),
            "referencia" to referencia,
            "operacion" to "DEBITO"
        ))
        
        try {
            val movimiento = MovimientoDto(
                cuenta = cuenta,
                monto = monto,
                tipoMovimiento = "DEBITO",
                referencia = referencia,
                fecha = java.time.Instant.now().toString()
            )
            
            val response = httpClient.post("$baseUrl/cuentas/movimientos") {
                contentType(ContentType.Application.Json)
                setBody(movimiento)
            }
            
            val resultado = response.body<ResultadoOperacionCuenta>()
            
            if (resultado.exitosa) {
                logs.registrar("Debito exitoso", mapOf(
                    "traceId" to traceId,
                    "nuevoSaldo" to (resultado.nuevoSaldo?.toString() ?: "N/A"),
                    "codigoOperacion" to (resultado.codigoOperacion ?: "N/A")
                ))
                metrics.incrementar("cuentas.debito.exitoso")
                Result.success(resultado)
            } else {
                logs.registrar("Debito fallido", mapOf(
                    "traceId" to traceId,
                    "mensaje" to resultado.mensaje
                ))
                metrics.incrementar("cuentas.debito.fallido")
                Result.failure(Exception(resultado.mensaje))
            }
        } catch (e: Exception) {
            logs.registrar("Error en debito de cuenta", mapOf(
                "traceId" to traceId,
                "error" to e.message,
                "tipoError" to e::class.simpleName ?: "Unknown"
            ))
            metrics.incrementar("cuentas.debito.error")
            Result.failure(e)
        }
    }
    
    suspend fun creditarCuenta(cuenta: String, monto: BigDecimal, referencia: String): Result<ResultadoOperacionCuenta> = withContext(Dispatchers.IO) {
        val traceId = traces.generarTraceId()
        logs.registrar("Iniciando credito en cuenta", mapOf(
            "traceId" to traceId,
            "cuenta" to cuenta,
            "monto" to monto.toString(),
            "referencia" to referencia,
            "operacion" to "CREDITO"
        ))
        
        try {
            val movimiento = MovimientoDto(
                cuenta = cuenta,
                monto = monto,
                tipoMovimiento = "CREDITO",
                referencia = referencia,
                fecha = java.time.Instant.now().toString()
            )
            
            val response = httpClient.post("$baseUrl/cuentas/movimientos") {
                contentType(ContentType.Application.Json)
                setBody(movimiento)
            }
            
            val resultado = response.body<ResultadoOperacionCuenta>()
            
            if (resultado.exitosa) {
                logs.registrar("Credito exitoso", mapOf(
                    "traceId" to traceId,
                    "nuevoSaldo" to (resultado.nuevoSaldo?.toString() ?: "N/A"),
                    "codigoOperacion" to (resultado.codigoOperacion ?: "N/A")
                ))
                metrics.incrementar("cuentas.credito.exitoso")
                Result.success(resultado)
            } else {
                logs.registrar("Credito fallido", mapOf(
                    "traceId" to traceId,
                    "mensaje" to resultado.mensaje
                ))
                metrics.incrementar("cuentas.credito.fallido")
                Result.failure(Exception(resultado.mensaje))
            }
        } catch (e: Exception) {
            logs.registrar("Error en credito de cuenta", mapOf(
                "traceId" to traceId,
                "error" to e.message,
                "tipoError" to e::class.simpleName ?: "Unknown"
            ))
            metrics.incrementar("cuentas.credito.error")
            Result.failure(e)
        }
    }
    
    suspend fun consultarSaldo(cuenta: String): Result<BigDecimal> = withContext(Dispatchers.IO) {
        val traceId = traces.generarTraceId()
        logs.registrar("Consultando saldo de cuenta", mapOf(
            "traceId" to traceId,
            "cuenta" to cuenta
        ))
        
        try {
            val response = httpClient.get("$baseUrl/cuentas/$cuenta/saldo")
            val cuentaDto = response.body<CuentaDto>()
            metrics.incrementar("cuentas.saldo.consultado")
            Result.success(cuentaDto.saldo)
        } catch (e: Exception) {
            logs.registrar("Error al consultar saldo", mapOf(
                "traceId" to traceId,
                "error" to e.message
            ))
            metrics.incrementar("cuentas.saldo.error")
            Result.failure(e)
        }
    }
    
    suspend fun validarCuenta(cuenta: String): Result<Boolean> = withContext(Dispatchers.IO) {
        val traceId = traces.generarTraceId()
        logs.registrar("Validando cuenta", mapOf(
            "traceId" to traceId,
            "cuenta" to cuenta
        ))
        
        try {
            val response = httpClient.get("$baseUrl/cuentas/$cuenta/validar")
            val esValida = response.body<Boolean>()
            metrics.incrementar("cuentas.validacion.exitosa")
            Result.success(esValida)
        } catch (e: Exception) {
            logs.registrar("Error al validar cuenta", mapOf(
                "traceId" to traceId,
                "error" to e.message
            ))
            metrics.incrementar("cuentas.validacion.error")
            Result.failure(e)
        }
    }
}

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/clients/NotificacionClient.kt ===
package com.banco.transferencia.infrastructure.clients

import com.banco.transferencia.infrastructure.observability.Metrics
import com.banco.transferencia.infrastructure.observability.Traces
import com.banco.transferencia.infrastructure.observability.Logs
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

data class NotificacionRequest(
    val destinatario: String,
    val mensaje: String,
    val tipo: String,
    val canal: String = "SMS",
    val prioridad: String = "NORMAL"
)

data class NotificacionResponse(
    val exitosa: Boolean,
    val mensajeId: String? = null,
    val mensaje: String
)

class NotificacionClient(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val metrics: Metrics,
    private val traces: Traces,
    private val logs: Logs
) {
    suspend fun enviarNotificacion(cuenta: String, mensaje: String, tipo: String): Result<NotificacionResponse> = withContext(Dispatchers.IO) {
        val traceId = traces.generarTraceId()
        logs.registrar("Enviando notificacion", mapOf(
            "traceId" to traceId,
            "cuenta" to cuenta,
            "tipo" to tipo,
            "mensaje" to mensaje.take(100)
        ))
        
        try {
            val request = NotificacionRequest(
                destinatario = cuenta,
                mensaje = mensaje,
                tipo = tipo,
                canal = determinarCanal(tipo),
                prioridad = determinarPrioridad(tipo)
            )
            
            val response = httpClient.post("$baseUrl/notificaciones/enviar") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            
            val resultado = response.body<NotificacionResponse>()
            
            if (resultado.exitosa) {
                logs.registrar("Notificacion enviada exitosamente", mapOf(
                    "traceId" to traceId,
                    "mensajeId" to (resultado.mensajeId ?: "N/A"),
                    "tipo" to tipo
                ))
                metrics.incrementar("notificaciones.enviada")
                metrics.incrementar("notificaciones.$tipo.enviada")
                Result.success(resultado)
            } else {
                logs.registrar("Notificacion fallida", mapOf(
                    "traceId" to traceId,
                    "mensaje" to resultado.mensaje,
                    "tipo" to tipo
                ))
                metrics.incrementar("notificaciones.fallida")
                Result.failure(Exception(resultado.mensaje))
            }
        } catch (e: Exception) {
            logs.registrar("Error al enviar notificacion", mapOf(
                "traceId" to traceId,
                "error" to e.message,
                "tipoError" to e::class.simpleName ?: "Unknown"
            ))
            metrics.incrementar("notificaciones.error")
            Result.failure(e)
        }
    }
    
    suspend fun enviarNotificacionMultiple(cuenta: String, mensajes: List<String>, tipo: String): Result<List<NotificacionResponse>> = withContext(Dispatchers.IO) {
        val traceId = traces.generarTraceId()
        logs.registrar("Enviando notificaciones multiples", mapOf(
            "traceId" to traceId,
            "cuenta" to cuenta,
            "cantidad" to mensajes.size.toString()
        ))
        
        try {
            val resultados = mensajes.map { msg ->
                val request = NotificacionRequest(
                    destinatario = cuenta,
                    mensaje = msg,
                    tipo = tipo,
                    canal = determinarCanal(tipo),
                    prioridad = determinarPrioridad(tipo)
                )
                
                val response = httpClient.post("$baseUrl/notificaciones/enviar") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
                
                response.body<NotificacionResponse>()
            }
            
            val exitosas = resultados.count { it.exitosa }
            logs.registrar("Notificaciones multiples completadas", mapOf(
                "traceId" to traceId,
                "total" to resultados.size.toString(),
                "exitosas" to exitosas.toString(),
                "fallidas" to (resultados.size - exitosas).toString()
            ))
            
            metrics.incrementar("notificaciones.multiples.enviada", resultados.size.toLong())
            Result.success(resultados)
        } catch (e: Exception) {
            logs.registrar("Error en notificaciones multiples", mapOf(
                "traceId" to traceId,
                "error" to e.message
            ))
            metrics.incrementar("notificaciones.multiples.error")
            Result.failure(e)
        }
    }
    
    private fun determinarCanal(tipo: String): String {
        return when (tipo) {
            "ALERTA" -> "EMAIL"
            "FRAUDE" -> "SMS"
            "DEBITO", "CREDITO" -> "PUSH"
            else -> "SMS"
        }
    }
    
    private fun determinarPrioridad(tipo: String): String {
        return when (tipo) {
            "FRAUDE" -> "URGENTE"
            "ALERTA" -> "ALTA"
            else -> "NORMAL"
        }
    }
}

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/persistence/TransferenciaRepositoryImpl.kt ===
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

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/config/ResilienceConfig.kt ===
package com.banco.transferencia.infrastructure.config

import io.github.resilience4j.bulkhead.Bulkhead
import io.github.resilience4j.bulkhead.BulkheadConfig
import io.github.resilience4j.bulkhead.BulkheadRegistry
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.github.resilience4j.retry.RetryRegistry
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import java.time.Duration

object ResilienceConfig {

    private val circuitBreakerRegistry: CircuitBreakerRegistry by lazy {
        val config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofSeconds(30))
            .slidingWindowSize(10)
            .minimumNumberOfCalls(5)
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build()
        CircuitBreakerRegistry.of(config)
    }

    private val retryRegistry: RetryRegistry by lazy {
        val config = RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(500))
            .retryExceptions(Exception::class.java)
            .build()
        RetryRegistry.of(config)
    }

    private val bulkheadRegistry: BulkheadRegistry by lazy {
        val config = BulkheadConfig.custom()
            .maxConcurrentCalls(10)
            .maxWaitDuration(Duration.ofMillis(2000))
            .build()
        BulkheadRegistry.of(config)
    }

    fun getCircuitBreaker(name: String): CircuitBreaker {
        return circuitBreaker.circuitBreaker(name)
    }

    fun getRetry(name: String): Retry {
        return retryRegistry.retry(name)
    }

    fun getBulkhead(name: String): Bulkhead {
        return bulkheadRegistry.bulkhead(name)
    }

    private val circuitBreaker: CircuitBreakerRegistry
        get() = circuitBreakerRegistry

    private val bulkhead: BulkheadRegistry
        get() = bulkheadRegistry

    fun configureApplication(application: Application) {
        application.apply {
            intercept(ApplicationCallPipeline.Plugins) {
                proceed()
            }
        }
    }

    private object ApplicationCallPipeline {
        object Plugins
    }

    fun getCircuitBreakerForClient(clientName: String): CircuitBreaker {
        val specificConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(60)
            .waitDurationInOpenState(Duration.ofSeconds(45))
            .slidingWindowSize(15)
            .minimumNumberOfCalls(8)
            .permittedNumberOfCallsInHalfOpenState(5)
            .build()
        
        val specificRegistry = CircuitBreakerRegistry.of(specificConfig)
        return specificRegistry.circuitBreaker(clientName)
    }

    fun getRetryForClient(clientName: String): Retry {
        val specificConfig = RetryConfig.custom()
            .maxAttempts(4)
            .waitDuration(Duration.ofMillis(800))
            .retryExceptions(IOException::class.java, TimeoutException::class.java)
            .build()
        
        val specificRegistry = RetryRegistry.of(specificConfig)
        return specificRegistry.retry(clientName)
    }

    fun getBulkheadForClient(clientName: String): Bulkhead {
        val specificConfig = BulkheadConfig.custom()
            .maxConcurrentCalls(5)
            .maxWaitDuration(Duration.ofMillis(1500))
            .build()
        
        val specificRegistry = BulkheadRegistry.of(specificConfig)
        return specificRegistry.bulkhead(clientName)
    }

    fun getAllCircuitBreakerNames(): List<String> {
        return circuitBreakerRegistry.allCircuitBreakers.map { it.name }
    }

    fun getAllRetryNames(): List<String> {
        return retryRegistry.allRetries.map { it.name }
    }

    fun getAllBulkheadNames(): List<String> {
        return bulkheadRegistry.allBulkheads.map { it.name }
    }
}

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/observability/Metrics.kt ===
package com.banco.transferencia.infrastructure.observability

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.DistributionSummary
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import java.util.concurrent.TimeUnit

class Metrics(
    private val registry: MeterRegistry
) {
    private val transferenciaIniciadaCounter: Counter = Counter.builder("transferencia.iniciada")
        .description("Número de transferencias iniciadas")
        .register(registry)

    private val transferenciaExitosaCounter: Counter = Counter.builder("transferencia.exitosa")
        .description("Número de transferencias completadas exitosamente")
        .register(registry)

    private val transferenciaFallidaCounter: Counter = Counter.builder("transferencia.fallida")
        .description("Número de transferencias fallidas")
        .register(registry)

    private val transferenciaRechazadaCounter: Counter = Counter.builder("transferencia.rechazada")
        .description("Número de transferencias rechazadas por validación")
        .register(registry)

    private val transferenciaDuracionTimer: Timer = Timer.builder("transferencia.duracion")
        .description("Tiempo de procesamiento de transferencias")
        .publishPercentiles(0.5, 0.95, 0.99)
        .register(registry)

    private val montoTransferidoSummary: DistributionSummary = DistributionSummary.builder("transferencia.monto")
        .description("Monto total transferido")
        .publishPercentiles(0.5, 0.95, 0.99)
        .register(registry)

    private val cuentaOrigenContador: Counter = Counter.builder("cuenta.operaciones")
        .tag("tipo", "origen")
        .description("Operaciones por cuenta origen")
        .register(registry)

    private val cuentaDestinoContador: Counter = Counter.builder("cuenta.operaciones")
        .tag("tipo", "destino")
        .description("Operaciones por cuenta destino")
        .register(registry)

    private val idempotenciaHits: Counter = Counter.builder("idempotencia.hits")
        .description("Claves de idempotencia encontradas")
        .register(registry)

    private val idempotenciaMisses: Counter = Counter.builder("idempotencia.misses")
        .description("Claves de idempotencia no encontradas")
        .register(registry)

    private val validacionTimer: Timer = Timer.builder("transferencia.validacion")
        .description("Tiempo de validación de transferencias")
        .register(registry)

    private val persistenciaTimer: Timer = Timer.builder("transferencia.persistencia")
        .description("Tiempo de persistencia de transferencias")
        .register(registry)

    fun registrarTransferenciaIniciada() {
        transferenciaIniciadaCounter.increment()
    }

    fun registrarTransferenciaExitosa() {
        transferenciaExitosaCounter.increment()
    }

    fun registrarTransferenciaFallida() {
        transferenciaFallidaCounter.increment()
    }

    fun registrarTransferenciaRechazada() {
        transferenciaRechazadaCounter.increment()
    }

    fun registrarDuracionTransferencia(duracionMillis: Long) {
        transferenciaDuracionTimer.record(duracionMillis, TimeUnit.MILLISECONDS)
    }

    fun registrarMonto(monto: Double) {
        montoTransferidoSummary.record(monto)
    }

    fun registrarOperacionCuentaOrigen() {
        cuentaOrigenContador.increment()
    }

    fun registrarOperacionCuentaDestino() {
        cuentaDestinoContador.increment()
    }

    fun registrarIdempotenciaHit() {
        idempotenciaHits.increment()
    }

    fun registrarIdempotenciaMiss() {
        idempotenciaMisses.increment()
    }

    fun registrarValidacionDuracion(duracionMillis: Long) {
        validacionTimer.record(duracionMillis, TimeUnit.MILLISECONDS)
    }

    fun registrarPersistenciaDuracion(duracionMillis: Long) {
        persistenciaTimer.record(duracionMillis, TimeUnit.MILLISECONDS)
    }

    companion object {
        fun inicializar(registry: MeterRegistry): Metrics {
            JvmMemoryMetrics().bindTo(registry)
            JvmGcMetrics().bindTo(registry)
            JvmThreadMetrics().bindTo(registry)
            ProcessorMetrics().bindTo(registry)
            return Metrics(registry)
        }
    }
}

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/observability/Traces.kt ===
package com.banco.transferencia.infrastructure.observability

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingSpanExporter
import org.slf4j.LoggerFactory
import java.time.Duration

object Traces {
    private val logger = LoggerFactory.getLogger(Traces::class.java)
    private var tracer: Tracer? = null
    private var openTelemetry: OpenTelemetry? = null

    fun inicializar(serviceName: String = "transferencia-service"): Tracer {
        logger.info("Inicializando trazas distribuidas para servicio: $serviceName")

        val tracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(createBatchSpanProcessor())
            .addSpanProcessor(SimpleSpanProcessor.create(OtlpJsonLoggingSpanExporter()))
            .build()

        openTelemetry = OpenTelemetrySdk.builder()
            .setTracerProvider(tracerProvider)
            .setPropagators(ContextPropagators.of(W3CTraceContextPropagator.getInstance()))
            .build()

        tracer = openTelemetry?.getTracer(serviceName, "1.0.0")

        logger.info("Trazas distribuidas inicializadas correctamente")
        return tracer ?: throw IllegalStateException("Failed to initialize tracer")
    }

    private fun createBatchSpanProcessor(): BatchSpanProcessor {
        val otlpExporter = OtlpGrpcSpanExporter.builder()
            .setEndpoint("http://localhost:4317")
            .setTimeout(Duration.ofSeconds(10))
            .build()

        return BatchSpanProcessor.builder(otlpExporter)
            .setScheduleDelayMillis(5000)
            .setMaxExportBatchSize(512)
            .setExporterTimeoutMillis(30000)
            .build()
    }

    fun obtenerTracer(): Tracer {
        return tracer ?: throw IllegalStateException("Tracer no inicializado. Llame a inicializar() primero.")
    }

    fun obtenerOpenTelemetry(): OpenTelemetry {
        return openTelemetry ?: throw IllegalStateException("OpenTelemetry no inicializado")
    }

    fun cerrar() {
        logger.info("Cerrando proveedor de trazas")
        (tracer as? io.opentelemetry.sdk.trace.SdkTracer)?.close()
        openTelemetry?.close()
    }
}

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/observability/Logs.kt ===
package com.banco.transferencia.infrastructure.observability

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import ch.qos.logback.core.encoder.LayoutWrappingEncoder
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy
import ch.qos.logback.core.util.FileSize
import net.logstash.logback.encoder.LogstashEncoder
import net.logstash.logback.layout.LogstashLayout
import org.slf4j.LoggerFactory
import java.io.File

object Logs {
    private val logger = LoggerFactory.getLogger(Logs::class.java)
    private var inicializado = false

    fun inicializar(nivelPorDefecto: String = "INFO") {
        if (inicializado) {
            logger.warn("Logs ya inicializados, omitiendo configuración duplicada")
            return
        }

        logger.info("Inicializando sistema de logs estructurados con nivel: $nivelPorDefecto")

        val context = LoggerFactory.getILoggerFactory() as LoggerContext
        context.stop()

        val rootLogger = context.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)
        rootLogger.level = ch.qos.logback.classic.Level.toLevel(nivelPorDefecto)

        val consoleAppender = crearAppenderConsola(context)
        rootLogger.addAppender(consoleAppender)

        val fileAppender = crearAppenderArchivo(context)
        rootLogger.addAppender(fileAppender)

        inicializado = true
        logger.info("Sistema de logs estructurados inicializado correctamente")
    }

    private fun crearAppenderConsola(context: LoggerContext): Appender<ILoggingEvent> {
        val encoder = LayoutWrappingEncoder<ILoggingEvent>().apply {
            this.context = context
            layout = crearLayoutJSON(context)
        }

        return ch.qos.logback.core.ConsoleAppender<ILoggingEvent>().apply {
            this.context = context
            name = "CONSOLE_JSON"
            encoder = encoder
            start()
        }
    }

    private fun crearAppenderArchivo(context: LoggerContext): Appender<ILoggingEvent> {
        val fileAppender = RollingFileAppender<ILoggingEvent>().apply {
            context = context
            name = "FILE_JSON"
            file = "/var/log/banco/transferencia/app.log"
            encoder = LayoutWrappingEncoder<ILoggingEvent>().apply {
                this.context = context
                layout = crearLayoutJSON(context)
            }
            rollingPolicy = crearPoliticaRotacion(context, this)
        }

        fileAppender.start()
        return fileAppender
    }

    private fun crearPoliticaRotacion(
        context: LoggerContext,
        parent: RollingFileAppender<ILoggingEvent>
    ): SizeAndTimeBasedRollingPolicy<ILoggingEvent> {
        return SizeAndTimeBasedRollingPolicy<ILoggingEvent>().apply {
            this.context = context
            setParent(parent)
            fileNamePattern = "/var/log/banco/transferencia/app.%d{yyyy-MM-dd}.%i.log.gz"
            maxFileSize = FileSize.valueOf("100MB")
            maxHistory = 30
            totalSizeCap = FileSize.valueOf("10GB")
        }
    }

    private fun crearLayoutJSON(context: LoggerContext): LogstashLayout {
        return LogstashLayout.Builder()
            .context(context)
            .customFields("{\"service\":\"transferencia\",\"environment\":\"${System.getenv("ENVIRONMENT") ?: "local\"}")
            .includeMdcKeyName("correlationId")
            .includeMdcKeyName("userId")
            .includeMdcKeyName("traceId")
            .build()
    }

    fun obtenerLogger(clase: Class<*>): Logger {
        return LoggerFactory.getLogger(clase) as Logger
    }

    fun estaInicializado(): Boolean = inicializado
}

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/TransferenciaController.kt ===
package com.banco.transferencia.infrastructure

import com.banco.transferencia.domain.Transferencia
import com.banco.transferencia.domain.TransferenciaRepository
import com.banco.transferencia.domain.TransferenciaRequest
import com.banco.transferencia.domain.TransferenciaResponse
import com.banco.transferencia.domain.EstadoTransferencia
import com.banco.transferencia.infrastructure.observability.Traces
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.request.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.path
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.UUID

class TransferenciaController(
    private val transferenciaRepository: TransferenciaRepository
) {
    private val logger = LoggerFactory.getLogger(TransferenciaController::class.java)
    private val tracer = Traces.obtenerTracer()

    fun registerRoutes(routing: Routing) {
        logger.info("Registrando rutas del controlador de transferencias")

        routing.route("/api/v1/transferencias") {
            post("/") { ejecutarTransferencia(call) }
            get("/") { listarTransferencias(call) }
            get("/{id}") { obtenerTransferencia(call) }
            get("/cuenta/{cuentaOrigen}") { listarPorCuentaOrigen(call) }
            put("/{id}/cancelar") { cancelarTransferencia(call) }
        }

        logger.info("Rutas registradas exitosamente")
    }

    private suspend fun ejecutarTransferencia(call: ApplicationCall) {
        val span = tracer.spanBuilder("POST /transferencias").startSpan()
        try {
            val idempotencyKey = call.request.header("Idempotency-Key")
            logger.info("Recibida solicitud de transferencia con clave de idempotencia: $idempotencyKey")

            if (idempotencyKey.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Header Idempotency-Key es requerido"))
                return
            }

            val existente = transferenciaRepository.buscarPorClaveIdempotencia(idempotenciaKey)
            if (existente != null) {
                logger.info("Transferencia existente encontrada para clave: $idempotenciaKey")
                call.respond(HttpStatusCode.OK, TransferenciaResponse.fromDominio(existente))
                return
            }

            val request = call.receive<TransferenciaRequest>()
            val transferencia = request.aDominio().conIdempotencia(idempotenciaKey)

            val validacion = transferencia.validar()
            if (validacion.isFailure) {
                val error = validacion.exceptionOrNull()?.message ?: "Error de validación"
                logger.warn("Validación fallida: $error")
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to error))
                return
            }

            val guardada = transferenciaRepository.guardar(transferencia)
            logger.info("Transferencia creada exitosamente con ID: ${guardada.id}")

            call.respond(HttpStatusCode.Created, TransferenciaResponse.fromDominio(guardada))
        } catch (e: BadRequestException) {
            logger.error("Solicitud malformada: ${e.message}")
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
        } catch (e: Exception) {
            logger.error("Error procesando transferencia: ${e.message}", e)
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error interno del servidor"))
        } finally {
            span.end()
        }
    }

    private suspend fun obtenerTransferencia(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de transferencia requerido"))
            return
        }

        val transferencia = transferenciaRepository.buscarPorId(id)
        if (transferencia == null) {
            logger.warn("Transferencia no encontrada: $id")
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Transferencia no encontrada"))
            return
        }

        call.respond(HttpStatusCode.OK, TransferenciaResponse.fromDominio(transferencia))
    }

    private suspend fun listarTransferencias(call: ApplicationCall) {
        val desdeStr = call.request.queryParameters["desde"]
        val hastaStr = call.request.queryParameters["hasta"]

        val transferencias = if (desdeStr != null && hastaStr != null) {
            val desde = Instant.parse(desdeStr)
            val hasta = Instant.parse(hastaStr)
            transferenciaRepository.listarPorRangoDeTiempo(desde, hasta)
        } else {
            emptyList()
        }

        val response = transferencias.map { TransferenciaResponse.fromDominio(it) }
        call.respond(HttpStatusCode.OK, response)
    }

    private suspend fun listarPorCuentaOrigen(call: ApplicationCall) {
        val cuentaOrigen = call.parameters["cuentaOrigen"]
        if (cuentaOrigen.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Número de cuenta requerido"))
            return
        }

        val transferencias = transferenciaRepository.listarPorCuentaOrigen(cuentaOrigen)
        val response = transferencias.map { TransferenciaResponse.fromDominio(it) }
        call.respond(HttpStatusCode.OK, response)
    }

    private suspend fun cancelarTransferencia(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de transferencia requerido"))
            return
        }

        val transferencia = transferenciaRepository.buscarPorId(id)
        if (transferencia == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Transferencia no encontrada"))
            return
        }

        if (!transferencia.puedeSerProcesada()) {
            call.respond(HttpStatusCode.Conflict, mapOf("error" to "La transferencia no puede ser cancelada en estado: ${transferencia.estado}"))
            return
        }

        val cancelada = transferencia.cambiarEstado(EstadoTransferencia.CANCELADA, "Cancelada por el usuario")
        val actualizada = transferenciaRepository.actualizar(cancelada)

        logger.info("Transferencia cancelada: $id")
        call.respond(HttpStatusCode.OK, TransferenciaResponse.fromDominio(actualizada))
    }
}

// === ARCHIVO: src/test/kotlin/com/banco/transferencia/application/TransferenciaServiceTest.kt ===
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

// === ARCHIVO: src/test/kotlin/com/banco/transferencia/infrastructure/clients/CuentasClientTest.kt ===
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

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/observability/Traces.kt ===
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

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/observability/Logs.kt ===
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

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/observability/Metrics.kt ===
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

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/application/TransferenciaService.kt ===
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

// === ARCHIVO: src/main/kotlin/com/banco/transferencia/infrastructure/clients/CuentasClient.kt ===
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

```
