package io.github.beombeom2.buildlogic.convention

import com.android.build.api.dsl.androidLibrary
import io.github.beombeom2.buildlogic.Versions
import io.github.beombeom2.buildlogic.model.AndroidLibraryConfig
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

fun KotlinMultiplatformExtension.configureLibraryTargets(
    xcfName: String,
    android: AndroidLibraryConfig,
) {
    androidLibrary {
        compileSdk = android.compileSdk
        minSdk = android.minSdk
    }

    targets.withType<KotlinAndroidTarget>().configureEach {
        compilerOptions {
            jvmTarget.set(Versions.JVM_TARGET)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = xcfName
            isStatic = false
        }
    }

    jvm("desktop")
}
