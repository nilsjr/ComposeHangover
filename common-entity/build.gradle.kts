@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    alias(libs.plugins.kotlin.serialization)
}

group = "de.nilsdruyen.compose"
version = "1.0-SNAPSHOT"

kotlin {
    androidTarget()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js(IR) {
        browser()
        binaries.executable()
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeHangoverCoreEntity"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.serial.json)
            }
        }
        val androidMain by getting {
        }
        val desktopMain by getting {
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val jsMain by getting {
        }
    }
}

android {
    namespace = "de.nilsdruyen.compose.common"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = false
    }
}