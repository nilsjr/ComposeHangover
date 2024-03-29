plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "de.nilsdruyen.compose.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "de.nilsdruyen.compose.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompilerJetpack.get()
    }
    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/LICENSE*",
                "META-INF/INDEX.LIST",
                "META-INF/io.netty.versions.properties",
            )
        }
    }
}

dependencies {
    implementation(projects.commonClient)

    with(libs.androidx) {
        implementation(platform(compose.bom))
        implementation(compose.activity)
        implementation(compose.ui)
        implementation(compose.material3)
        implementation(compose.material.icons)
        implementation(compose.animation)
        implementation(compose.animation.graphics)
        implementation(compose.constraint)

        debugImplementation(compose.ui.tooling)
        implementation(compose.ui.tooling.preview)
    }

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
}