plugins {
    kotlin("multiplatform") version "1.7.21" apply false
    kotlin("android") version "1.7.21" apply false
    id("com.google.devtools.ksp") version "1.7.21-1.0.8" apply false
    id("com.android.application") version "7.4.0-rc01" apply false
    id("com.android.library") version "7.4.0-rc01" apply false
    id("org.jetbrains.compose") version "1.3.0-beta04-dev885" apply false
    id("com.github.ben-manes.versions") version "0.44.0"
    id("com.rickclephas.kmp.nativecoroutines") version "0.13.1" apply false
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("com.google.cloud.tools.appengine") version("2.4.5") apply false
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            val arguments = listOf(
                "-progressive",
                "-Xopt-in=kotlin.RequiresOptIn"
            )
            freeCompilerArgs = freeCompilerArgs + arguments
            jvmTarget = "11"
        }
    }
}

tasks.register("runAll") {
    dependsOn(":backend:run", ":web:browserDevelopmentRun", ":desktop:run")
//    dependsOn(":backend:run", ":web:browserDevelopmentRun", ":android:assembleDebug", ":desktop:run")
}