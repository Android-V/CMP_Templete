
plugins {
    id("io.github.beombeom2.kmp.composeApplication")
    alias(libs.plugins.composeHotReload)
}

kmpApp {
    xcfName.set("ComposeApp")

    androidNamespace.set("io.github.beombeom2.cmp")
    androidApplicationId.set("io.github.beombeom2.cmp")

    versionCode.set(1)
    versionName.set("1.0")

    desktopMainClass.set("io.github.beombeom2.cmp.MainKt")
    desktopPackageName.set("io.github.beombeom2.cmp")
}

android {
    namespace = "io.github.beombeom2.cmp"
    defaultConfig {
        applicationId = "io.github.beombeom2.cmp"
    }
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.navigation.compose)

            implementation(project(":data"))
            implementation(project(":domain"))
            implementation(project(":core:designSystem"))
            implementation(project(":feature"))
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutinesSwing)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
