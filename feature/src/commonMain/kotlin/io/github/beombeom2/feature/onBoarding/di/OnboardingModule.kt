package io.github.beombeom2.feature.onBoarding.di

import io.github.beombeom2.feature.onBoarding.ui.OnboardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingModule = module {
    viewModelOf(::OnboardingViewModel)
}