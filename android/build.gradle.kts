plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "de.nilsdruyen.compose.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "de.nilsdruyen.compose.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    implementation(projects.commonClient)

    with(libs.androidx) {
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
}