package io.github.beombeom2.buildlogic.extension

import io.github.beombeom2.buildlogic.Versions
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject
import kotlin.jvm.java

abstract class KmpDataExtension @Inject constructor(objects: ObjectFactory) {

    val enableAndroid: Property<Boolean> = objects.property(Boolean::class.java).convention(true)
    val enableIos: Property<Boolean> = objects.property(Boolean::class.java).convention(true)
    val enableDesktop: Property<Boolean> = objects.property(Boolean::class.java).convention(true)

    val namespace: Property<String> = objects.property(String::class.java)
    val xcfName: Property<String> = objects.property(String::class.java)


    val androidCompileSdk: Property<Int> = objects.property(Int::class.java).convention(Versions.ANDROID_COMPILE_SDK)
    val androidMinSdk: Property<Int> = objects.property(Int::class.java).convention(Versions.ANDROID_MIN_SDK)

    val enableAndroidDeviceTestDeps: Property<Boolean> =
        objects.property(Boolean::class.java).convention(true)

    // presets
    val useKoinCore: Property<Boolean> = objects.property(Boolean::class.java).convention(true)
    val useKtorBundle: Property<Boolean> = objects.property(Boolean::class.java).convention(true)
    val useDataStore: Property<Boolean> = objects.property(Boolean::class.java).convention(true)

    val domainModulePath: Property<String> = objects.property(String::class.java)
}
