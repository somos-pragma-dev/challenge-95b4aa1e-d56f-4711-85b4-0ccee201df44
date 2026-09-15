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