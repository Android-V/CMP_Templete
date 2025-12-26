package io.github.beombeom2.data.firstLaunch.di

import io.github.beombeom2.data.firstLaunch.dataSource.FirstLaunchLocalDataSource
import io.github.beombeom2.data.firstLaunch.local.AndroidFirstLaunchLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val  androidFirstLaunchModule = module {
    single<FirstLaunchLocalDataSource> { AndroidFirstLaunchLocalDataSource(androidContext()) }
}
