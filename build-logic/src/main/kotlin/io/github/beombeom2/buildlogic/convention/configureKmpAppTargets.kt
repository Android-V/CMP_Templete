package io.github.beombeom2.buildlogic.convention

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

fun KotlinMultiplatformExtension.configureKmpAppTargets(
    xcfName: String,
    jvmTarget: JvmTarget = JvmTarget.JVM_11,
) {
    androidTarget {
        compilerOptions {
            this.jvmTarget.set(jvmTarget)
        }
    }

    fun KotlinNativeTarget.configureFramework() {
        binaries.framework {
            baseName = xcfName
            isStatic = true
        }
    }

    iosX64 { configureFramework() }
    iosArm64 { configureFramework() }
    iosSimulatorArm64 { configureFramework() }

    jvm("desktop")
}