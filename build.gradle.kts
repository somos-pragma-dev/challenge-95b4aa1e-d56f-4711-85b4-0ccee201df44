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