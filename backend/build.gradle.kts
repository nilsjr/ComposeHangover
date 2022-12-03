@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}
val appName = "de.nilsdruyen.compose.ApplicationKt"
application {
    mainClass.set(appName)
}
dependencies {
    implementation(projects.commonEntity)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.html.builder)
    implementation(libs.ktor.server.compression)
    implementation(libs.ktor.server.content)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.logging)
    implementation(libs.ktor.serial.json)
}