import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.kotlinx.coroutines.guava)
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                
                implementation(libs.jsoup)
                implementation(libs.coil.compose)
                implementation(libs.coil.network.okhttp)
                implementation(libs.okhttp)
                
                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
                
                implementation(libs.lifecycle.viewmodel)
                implementation(libs.lifecycle.viewmodel.compose)
            }
        }
    }
}

room {
    schemaDirectory("${System.getProperty("java.io.tmpdir")}/room_schemas/linkvault")
}

dependencies {
    add("kspCommonMainMetadata", libs.room.compiler)
    add("kspDesktop", libs.room.compiler)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe)
            packageName = "LinkVault"
            packageVersion = "1.0.0"
        }
    }
}
