package io.github.beombeom2.buildlogic.convention

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.android.build.api.dsl.androidLibrary
import io.github.beombeom2.buildlogic.model.AndroidLibraryConfig
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

fun KotlinMultiplatformExtension.configureLibraryTargets(
    xcfName: String,
    android: AndroidLibraryConfig,
    androidLibraryAdditional: KotlinMultiplatformAndroidLibraryExtension.() -> Unit = {},
) {
    androidLibrary {
        namespace = android.namespace
        compileSdk = android.compileSdk
        minSdk = android.minSdk

        withHostTestBuilder { }

        withDeviceTestBuilder {
            sourceSetTreeName = android.deviceTestSourceSetTreeName
        }.configure {
            instrumentationRunner = android.instrumentationRunner
        }

        androidLibraryAdditional()
    }

    targets.withType(KotlinAndroidTarget::class.java).configureEach {
        compilerOptions {
            jvmTarget.set(Versions.JVM_TARGET)
        }
    }

    fun KotlinNativeTarget.configureFramework() {
        binaries.framework {
            baseName = xcfName
            isStatic = false
        }
    }

    iosX64 { configureFramework() }
    iosArm64 { configureFramework() }
    iosSimulatorArm64 { configureFramework() }

    jvm("desktop")
}