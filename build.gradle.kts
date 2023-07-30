import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.9.0" apply false
    kotlin("android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.12" apply false
    id("com.android.application") version "8.2.0-alpha14" apply false
    id("com.android.library") version "8.2.0-alpha14" apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.gradle.versions) apply false
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-5" apply false
    id("com.github.johnrengelman.shadow") version "8.1.0" apply false
    id("com.google.cloud.tools.appengine") version("2.4.5") apply false
}

subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            progressiveMode.set(true)
        }
        kotlinOptions {
            val arguments = listOf(
                "-opt-in=kotlin.RequiresOptIn"
            )
            freeCompilerArgs = freeCompilerArgs + arguments
        }
    }
}

tasks.register("runAll") {
    dependsOn(":backend:run", ":web:browserDevelopmentRun", ":desktop:run")
//    dependsOn(":backend:run", ":web:browserDevelopmentRun", ":android:assembleDebug", ":desktop:run")
}