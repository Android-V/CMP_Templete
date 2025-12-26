package io.github.beombeom2.data.firstLaunch.di

import io.github.beombeom2.data.firstLaunch.dataSource.FirstLaunchLocalDataSource
import io.github.beombeom2.data.firstLaunch.local.IosFirstLaunchLocalDataSource
import org.koin.dsl.module

val iosFirstLaunchModule = module {
    single<FirstLaunchLocalDataSource> { IosFirstLaunchLocalDataSource() }
}