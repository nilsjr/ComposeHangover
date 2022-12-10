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

compose {
    kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:1.4.0-alpha02")
}

dependencies {
    implementation(projects.commonClient)

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