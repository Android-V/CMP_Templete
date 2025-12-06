package io.github.beombeom2.domain.firstAppLaunch.repository

import kotlinx.coroutines.flow.Flow


interface FirstAppLaunchRepository {
    val isFirstLaunchFlow: Flow<Boolean>
    suspend fun setOnboardingSeen()
}