package io.github.beombeom2.data.firstLaunch.di

import io.github.beombeom2.data.firstLaunch.repository.FirstLaunchRepositoryImpl
import io.github.beombeom2.domain.firstLaunch.repository.FirstLaunchRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val firstLaunchDataModule = module {
    singleOf(::FirstLaunchRepositoryImpl) bind FirstLaunchRepository::class
}