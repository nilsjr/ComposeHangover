import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(projects.commonClient)

                implementation(compose.runtime)
                implementation(compose.html.core)

                implementation(npm("chart.js", "3.9.1", generateExternals = false))
            }
        }
    }
}

compose {
    kotlinCompilerPlugin.set("1.5.0")
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