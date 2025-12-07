package io.github.beombeom2.feature.splash.di

import io.github.beombeom2.feature.splash.ui.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashModule = module {
    viewModelOf(::SplashViewModel)
}