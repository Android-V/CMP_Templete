package io.github.beombeom2.buildlogic.plugin

import com.android.build.gradle.LibraryExtension
import io.github.beombeom2.buildlogic.extension.KmpFeatureExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


class KmpFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.multiplatform")
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.compose")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")


        val ext = extensions.create<KmpFeatureExtension>("featurePlugin").apply {
            enableAndroid.convention(true)
            enableIos.convention(true)
            enableDesktop.convention(true)

            useComposePreset.convention(true)
            useKoinPreset.convention(true)
            useNavigationPreset.convention(true)
            useLifecyclePreset.convention(true)
            useCoroutinesPreset.convention(true)

            namespace.convention("io.github.beombeom2.kmp.feature")
            xcfName.convention("feature:FeatureKit")
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        extensions.configure<KotlinMultiplatformExtension> {
            if (ext.enableAndroid.get()) {
                androidTarget()
            }

            if (ext.enableIos.get()) {
                iosX64 {
                    binaries.framework { baseName = ext.xcfName.get() }
                }
                iosArm64 {
                    binaries.framework { baseName = ext.xcfName.get() }
                }
                iosSimulatorArm64 {
                    binaries.framework { baseName = ext.xcfName.get() }
                }
            }

            if (ext.enableDesktop.get()) {
                jvm("desktop")
            }

            sourceSets.apply {
                val commonMain = getByName("commonMain")
                val commonTest = getByName("commonTest")

                val androidMain = runCatching { getByName("androidMain") }.getOrElse { maybeCreate("androidMain") }
                val iosMain = runCatching { getByName("iosMain") }.getOrElse { maybeCreate("iosMain") }

                commonMain.dependencies {
                    if (ext.useComposePreset.get()) {
                        implementation(libs.findLibrary("compose.runtime").get())
                        implementation(libs.findLibrary("compose.foundation").get())
                        implementation(libs.findLibrary("compose.material3").get())
                        implementation(libs.findLibrary("compose.ui").get())
                        implementation(libs.findLibrary("compose-resources").get())
                        implementation(libs.findLibrary("compose-ui-toolingPreview").get())
                        implementation(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                    }

                    if (ext.useKoinPreset.get()) {
                        implementation(libs.findLibrary("koin.compose").get())
                        implementation(libs.findLibrary("koin.compose.viewmodel").get())
                    }

                    if (ext.useNavigationPreset.get()) {
                        implementation(libs.findLibrary("navigation.compose").get())
                    }

                    if (ext.useLifecyclePreset.get()) {
                        implementation(libs.findLibrary("lifecycle.viewmodel").get())
                    }

                    if (ext.useCoroutinesPreset.get()) {
                        implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                    }
                }

                commonTest.dependencies {
                    implementation(libs.findLibrary("kotlin.test").get())
                }

                androidMain.dependencies {
                    if (ext.useComposePreset.get()) {
                        implementation(libs.findLibrary("compose-ui-toolingPreview").get())
                        implementation(libs.findLibrary("androidx.activity.compose").get())
                    }
                    if (ext.useKoinPreset.get()) {
                        implementation(libs.findLibrary("koin.android").get())
                    }
                }

                iosMain.dependencies {
                }

                val androidDeviceTest = runCatching { getByName("androidDeviceTest") }.getOrNull()
                androidDeviceTest?.dependencies {
                    implementation(libs.findLibrary("androidx.runner").get())
                    implementation(libs.findLibrary("androidx.core").get())
                    implementation(libs.findLibrary("androidx.testExt.junit").get())
                }
            }
        }

        extensions.configure<LibraryExtension> {
            namespace = ext.namespace.get()
        }
    }
}
