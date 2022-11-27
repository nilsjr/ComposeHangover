plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
}

group = "de.nilsdruyen.compose"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.websockets)
            }
        }
        val androidMain by getting {
            dependencies {

            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
            }
        }
    }
}

android {
    namespace = "de.nilsdruyen.compose.common"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}