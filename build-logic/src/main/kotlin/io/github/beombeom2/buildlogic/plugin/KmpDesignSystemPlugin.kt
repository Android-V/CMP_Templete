package io.github.beombeom2.buildlogic.plugin

import com.android.build.api.dsl.androidLibrary
import io.github.beombeom2.buildlogic.extension.KmpDesignSystemExtension
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpDesignSystemPlugin : BasePlugin() {
    override fun apply(target: Project) = with(target) {
        applyPlugin("org.jetbrains.kotlin.multiplatform")
        applyPlugin("com.android.kotlin.multiplatform.library")
        applyPlugin("org.jetbrains.compose")
        applyPlugin("org.jetbrains.kotlin.plugin.compose")

        val ext = extensions.create("designSystem", KmpDesignSystemExtension::class.java).apply {
            namespace.convention(deriveNamespace())
            xcfName.convention(deriveXcfName())
        }

        extensions.configure<KotlinMultiplatformExtension> {
            if (ext.enableAndroid.get()) {
                androidLibrary {
                    val ns = ext.namespace.orNull?.trim().orEmpty()
                    if (ns.isBlank()) {
                        throw GradleException("designSystem.namespace is required (AGP 8+).")
                    }

                    namespace = ns
                    compileSdk = ext.androidCompileSdk.get()
                    minSdk = ext.androidMinSdk.get()

                    if (ext.enableHostTests.get()) {
                        withHostTestBuilder { }
                    }

                    if (ext.enableDeviceTests.get()) {
                        withDeviceTestBuilder {
                            sourceSetTreeName = "test"
                        }.configure {
                            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                        }
                    }
                }
            }

            if (ext.enableIos.get()) {
                iosX64 { binaries.framework { baseName = ext.xcfName.get() } }
                iosArm64 { binaries.framework { baseName = ext.xcfName.get() } }
                iosSimulatorArm64 { binaries.framework { baseName = ext.xcfName.get() } }
            }

            if (ext.enableDesktop.get()) {
                jvm("desktop")
            }

            sourceSets.apply {
                val commonMain = getByName("commonMain")
                val commonTest = getByName("commonTest")

                val androidMain = findByName("androidMain")
                val androidDebug = findByName("androidDebug")
                val androidDeviceTest = findByName("androidDeviceTest")

                commonMain.dependencies {
                    implementation(libs.findLibrary("compose-runtime").get())
                    implementation(libs.findLibrary("compose-foundation").get())
                    implementation(libs.findLibrary("compose-ui").get())
                    implementation(libs.findLibrary("compose-material3").get())
                    implementation(libs.findLibrary("compose-resources").get())
                    implementation(libs.findLibrary("compose-ui-toolingPreview").get())

                    if (ext.useKoin.get()) {
                        implementation(libs.findLibrary("koin-core").get())
                        implementation(libs.findLibrary("koin-compose").get())
                    }
                }

                commonTest.dependencies {
                    implementation(libs.findLibrary("kotlin-test").get())
                }

                androidMain?.dependencies {
                    implementation(libs.findLibrary("compose-ui-toolingPreview").get())
                }

                androidDebug?.dependencies {
                    implementation(libs.findLibrary("compose-ui-tooling").get())
                }

                if (ext.enableDeviceTests.get() && ext.enableAndroidDeviceTestDeps.get()) {
                    androidDeviceTest?.dependencies {
                        implementation(libs.findLibrary("androidx-runner").get())
                        implementation(libs.findLibrary("androidx-core").get())
                        implementation(libs.findLibrary("androidx-testExt-junit").get())
                    }
                }
            }
        }
    }
}