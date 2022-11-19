import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("js")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
}

dependencies {
    implementation(compose.runtime)
    implementation(compose.web.core)

    implementation(npm("chart.js", "3.9.1", generateExternals = false))
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        val arguments = listOf(
            "-progressive",
            "-Xopt-in=kotlin.RequiresOptIn"
        )
        freeCompilerArgs = freeCompilerArgs + arguments
        jvmTarget = "11"
    }
}