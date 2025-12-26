package io.github.beombeom2.data.firstLaunch.di

import io.github.beombeom2.data.firstLaunch.dataSource.FirstLaunchLocalDataSource
import io.github.beombeom2.data.firstLaunch.local.DesktopFirstLaunchLocalDataSource
import org.koin.dsl.module

val desktopFirstLaunchModule = module {
    single<FirstLaunchLocalDataSource> { DesktopFirstLaunchLocalDataSource() }
}