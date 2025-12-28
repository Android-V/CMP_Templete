package io.github.beombeom2.buildlogic.extension

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class KmpAppExtension @Inject constructor(objects: ObjectFactory) {

    val xcfName: Property<String> =
        objects.property(String::class.java).convention("ComposeApp")

    val androidNamespace: Property<String> = objects.property(String::class.java)
    val androidApplicationId: Property<String> = objects.property(String::class.java)

    val versionCode: Property<Int> =
        objects.property(Int::class.java).convention(1)

    val versionName: Property<String> =
        objects.property(String::class.java).convention("1.0")

    val desktopMainClass: Property<String> = objects.property(String::class.java)
    val desktopPackageName: Property<String> = objects.property(String::class.java)
    val desktopPackageVersion: Property<String> =
        objects.property(String::class.java).convention("1.0.0")
}
