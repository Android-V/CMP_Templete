package io.github.beombeom2.cmp

import androidx.compose.ui.window.ComposeUIViewController
import io.github.beombeom2.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}