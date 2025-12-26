package io.github.beombeom2.domain.firstLaunch.di

import io.github.beombeom2.domain.firstLaunch.usecase.GetFirstLaunchUseCase
import io.github.beombeom2.domain.firstLaunch.usecase.PostFirstLaunchUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val firstLaunchDomainModule = module {
    singleOf(::GetFirstLaunchUseCase)
    singleOf(::PostFirstLaunchUseCase)
}