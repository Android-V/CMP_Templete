package io.github.beombeom2.cmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.beombeom2.di.initKoin

fun main()  {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMP_Templete",
        ) {
            App()
        }
    }
}