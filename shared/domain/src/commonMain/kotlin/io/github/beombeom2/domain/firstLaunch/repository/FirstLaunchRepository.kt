package io.github.beombeom2.domain.firstLaunch.repository

import kotlinx.coroutines.flow.Flow


interface FirstLaunchRepository {
    val isFirstLaunchFlow: Flow<Boolean>
    suspend fun setOnboardingSeen()
}