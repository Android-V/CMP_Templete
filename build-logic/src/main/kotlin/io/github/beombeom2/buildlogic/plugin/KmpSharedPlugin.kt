package io.github.beombeom2.buildlogic.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpSharedPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.multiplatform")
        pluginManager.apply("com.android.library")

        val kotlinExt = extensions.getByType<KotlinMultiplatformExtension>()
        kotlinExt.apply {
            androidTarget()
            iosArm64()
            iosSimulatorArm64()
            jvm("desktop")

            sourceSets {
                val commonMain = getByName("commonMain")
                commonMain.dependencies {
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
                }
            }
        }
    }
}
