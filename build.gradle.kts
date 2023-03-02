plugins {
    kotlin("multiplatform") version "1.8.10" apply false
    kotlin("android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id("com.android.application") version "8.0.0-beta04" apply false
    id("com.android.library") version "8.0.0-beta04" apply false
    id("org.jetbrains.compose") version "1.4.0-alpha01-dev958" apply false
    id("com.github.ben-manes.versions") version "0.46.0"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-5" apply false
    id("com.github.johnrengelman.shadow") version "8.1.0" apply false
    id("com.google.cloud.tools.appengine") version("2.4.5") apply false
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            val arguments = listOf(
                "-progressive",
                "-opt-in=kotlin.RequiresOptIn"
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