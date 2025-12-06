package io.github.beombeom2.data.firstAppLaunch.dataSource

import kotlinx.coroutines.flow.Flow

interface FirstAppLaunchLocalDataSource {
    val isFirstLaunchFlow: Flow<Boolean>
    suspend fun setOnboardingSeen()
}