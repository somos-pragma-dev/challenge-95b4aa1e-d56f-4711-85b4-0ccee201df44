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