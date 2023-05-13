import info.solidsoft.gradle.pitest.PitestPluginExtension

plugins {
    kotlin("jvm") version "1.8.0"
    id("info.solidsoft.pitest") version "1.9.0"
}

group = "com.github.hugovallada"
version = "1.0"

val kotestVersion = "5.6.2"

repositories {
    mavenCentral()
}

configure<PitestPluginExtension> {
    junit5PluginVersion.set("1.0.0")
    avoidCallsTo.set(setOf("kotlin.jvm.internal"))
    mutators.set(setOf("STRONGER"))
    targetClasses.set(setOf("com.github.hugovallada.kotest.domain.*"))
    targetTests.set(setOf("com.github.hugovallada.kotest.domain.*"))
    threads.set(Runtime.getRuntime().availableProcessors())
    outputFormats.set(setOf("XML", "HTML"))
    mutationThreshold.set(75)
    coverageThreshold.set(60)
}

dependencies {
    testImplementation("org.pitest:pitest-junit5-plugin:0.12")
    testImplementation("org.instancio:instancio:1.0.4")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}