package io.github.beombeom2.di

import io.github.beombeom2.data.firstLaunch.di.firstLaunchDataModule
import io.github.beombeom2.domain.firstLaunch.di.firstLaunchDomainModule
import io.github.beombeom2.feature.onBoarding.di.onboardingModule
import io.github.beombeom2.feature.splash.di.splashModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)

        modules(
            // domain
            firstLaunchDomainModule,

            // data
            firstLaunchDataModule,

            // feature
            splashModule,
            onboardingModule,
            //loginModule,
            //platformModule,
        )
    }
}