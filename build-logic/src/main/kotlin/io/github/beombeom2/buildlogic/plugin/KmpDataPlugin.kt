package io.github.beombeom2.buildlogic.plugin

import com.android.build.api.dsl.androidLibrary
import io.github.beombeom2.buildlogic.extension.KmpDataExtension
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

class KmpDataPlugin : BasePlugin() {
    override fun apply(target: Project) = with(target) {
        applyPlugin("org.jetbrains.kotlin.multiplatform")
        applyPlugin("com.android.kotlin.multiplatform.library")
        applyPlugin("org.jetbrains.kotlin.plugin.serialization")

        val ext = extensions.create("dataPlugin", KmpDataExtension::class.java).apply {
            namespace.convention(project.deriveNamespace())
            xcfName.convention(project.deriveXcfName())
        }

        var commonMain: KotlinSourceSet? = null

        extensions.configure<KotlinMultiplatformExtension> {
            if (ext.enableAndroid.get()) {
                androidLibrary {
                    val ns = ext.namespace.orNull?.trim().orEmpty()
                    if (ns.isBlank()) {
                        throw GradleException("dataPlugin.namespace is required (AGP 8+).")
                    }

                    namespace = ns
                    compileSdk = ext.androidCompileSdk.get()
                    minSdk = ext.androidMinSdk.get()

                    withDeviceTestBuilder { sourceSetTreeName = "test" }.configure {
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

            sourceSets.apply {
                commonMain = getByName("commonMain")
                val desktopMain = findByName("desktopMain")

                val iosMain = maybeCreate("iosMain")
                val iosTest = maybeCreate("iosTest")
                if (ext.enableIos.get()) linkToIosShared(iosMain, iosTest)

                val androidMain = findByName("androidMain")

                commonMain.dependencies {
                    if (ext.useKoinCore.get()) {
                        api(project.libs.findLibrary("koin-core").orElseThrow())
                    }
                }

                androidMain?.dependencies {
                    if (ext.useKoinCore.get() && ext.enableAndroid.get()) {
                        implementation(project.libs.findLibrary("koin-android").orElseThrow())
                    }
                    if (ext.useDataStore.get() && ext.enableAndroid.get()) {
                        implementation(project.libs.findLibrary("datastore-preferences").orElseThrow())
                        implementation(project.libs.findLibrary("datastore").orElseThrow())
                    }
                }

                desktopMain?.dependencies {
                    implementation(project.libs.findLibrary("kotlinx-coroutinesSwing").orElseThrow())
                }
            }
        }

        gradle.projectsEvaluated {
            val domainPath = resolveDomainPath(ext)
            if (domainPath.isBlank()) return@projectsEvaluated

            if (findProject(domainPath) == null) {
                throw GradleException(
                    "dataPlugin.domainModulePath='$domainPath' not found. " +
                            "Check settings.gradle(.kts) include(...) and module path spelling."
                )
            }

            val cm = commonMain
                ?: throw GradleException("KMP commonMain sourceSet not found. Is kotlin-multiplatform plugin applied?")

            cm.dependencies {
                implementation(project(domainPath))
            }
        }
    }

    private fun Project.resolveDomainPath(ext: KmpDataExtension): String {
        val explicit = ext.domainModulePath.orNull?.trim().orEmpty()
        if (explicit.isNotBlank()) return explicit

        val seg = pathSegments()
        if (seg.size >= 2 && seg.first() == "data") {
            return ":" + listOf("domain", seg[1]).joinToString(":")
        }
        return ""
    }

    private fun KotlinMultiplatformExtension.linkToIosShared(
        iosMain: KotlinSourceSet,
        iosTest: KotlinSourceSet,
    ) {
        val targets = listOf("iosX64", "iosArm64", "iosSimulatorArm64")
        targets.forEach { t ->
            sourceSets.findByName("${t}Main")?.dependsOn(iosMain)
            sourceSets.findByName("${t}Test")?.dependsOn(iosTest)
        }
    }
}