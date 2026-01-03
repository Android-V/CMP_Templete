package io.github.beombeom2.buildlogic.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

abstract class BasePlugin : Plugin<Project> {

    protected val Project.libs: VersionCatalog
        get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

    protected fun Project.applyPlugin(pluginId: String) {
        plugins.apply(pluginId)
    }

    protected fun Project.pathSegments(): List<String> =
        path.split(":").filter { it.isNotBlank() }

    protected fun Project.deriveNamespace(prefix: String = "io.github.beombeom2"): String =
        pathSegments().joinToString(".", prefix = "$prefix.")

    protected fun Project.deriveXcfName(suffix: String = "Kit"): String =
        pathSegments().joinToString(":") + suffix

    abstract override fun apply(target: Project)
}
