package io.github.beombeom2.buildlogic.plugin

import com.android.build.api.dsl.androidLibrary
import io.github.beombeom2.buildlogic.extension.KmpDataExtension
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.internal.Actions.with
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpDataPlugin : BasePlugin() {
    override fun apply(target: Project) {
        with(target) {
            applyPlugin("org.jetbrains.kotlin.multiplatform")
            applyPlugin("com.android.kotlin.multiplatform.library")
            applyPlugin("org.jetbrains.kotlin.plugin.serialization")

            val ext = extensions.create("dataPlugin", KmpDataExtension::class.java).apply {
                namespace.convention(project.deriveNamespace())
                xcfName.convention(project.deriveXcfName())
            }

            extensions.configure<KotlinMultiplatformExtension> {
                if (ext.enableAndroid.get()) {
                    androidLibrary {
                        val ns = ext.namespace.orNull?.trim().orEmpty()
                        if (ns.isBlank()) throw GradleException("dataPlugin.namespace is required (AGP 8+).")

                        namespace = ns
                        compileSdk = ext.androidCompileSdk.get()
                        minSdk = ext.androidMinSdk.get()

                        withDeviceTestBuilder {
                            sourceSetTreeName = "test"
                        }.configure {
                            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

                afterEvaluate {
                    val path = ext.domainModulePath.orNull ?: return@afterEvaluate
                    sourceSets.getByName("commonMain").dependencies {
                        implementation(project(path))
                    }
                }

                sourceSets.apply {
                    val commonMain = getByName("commonMain")
                    val commonTest = getByName("commonTest")

                    val androidMain = findByName("androidMain")
                    val iosMain = maybeCreate("iosMain")
                    val desktopMain = maybeCreate("desktopMain")

                    commonMain.dependencies {
                        if (ext.useKoinCore.get()) {
                            api(project.libs.findLibrary("koin-core").orElseThrow())
                        }
                        if (ext.useKtorBundle.get()) {
                            implementation(project.libs.findBundle("ktor").orElseThrow())
                        }
                    }

                    commonTest.dependencies {
                        implementation(project.libs.findLibrary("kotlin-test").orElseThrow())
                    }

                    androidMain?.dependencies {
                        if (ext.useDataStore.get()) {
                            api(project.libs.findLibrary("datastore-preferences").orElseThrow())
                        }
                        implementation(project.libs.findLibrary("koin-android").orElseThrow())
                        implementation(project.libs.findLibrary("ktor-client-okhttp").orElseThrow())
                    }

                    iosMain.dependencies {
                        implementation(project.libs.findLibrary("ktor-client-darwin").orElseThrow())
                    }

                    desktopMain.dependencies {
                        implementation(project.libs.findLibrary("kotlinx-coroutinesSwing").orElseThrow())
                        implementation(project.libs.findLibrary("oshi-core").orElseThrow())
                        implementation(project.libs.findLibrary("ktor-client-okhttp").orElseThrow())
                    }

                    if (ext.enableAndroidDeviceTestDeps.get()) {
                        findByName("androidDeviceTest")?.dependencies {
                            implementation(project.libs.findLibrary("androidx-runner").orElseThrow())
                            implementation(project.libs.findLibrary("androidx-core").orElseThrow())
                            implementation(project.libs.findLibrary("androidx-testExt-junit").orElseThrow())
                        }
                    }
                }
            }
        }
    }
}
