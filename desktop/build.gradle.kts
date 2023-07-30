import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "de.nilsdruyen.compose"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(17)
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.commonClient)

                implementation(compose.desktop.currentOs)
            }
        }
    }
}

compose {
    kotlinCompilerPlugin.set("1.5.0")

    desktop {
        application {
            mainClass = "MainKt"
            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "ComposeHangover"
                packageVersion = "1.0.0"
            }
        }
    }
}
