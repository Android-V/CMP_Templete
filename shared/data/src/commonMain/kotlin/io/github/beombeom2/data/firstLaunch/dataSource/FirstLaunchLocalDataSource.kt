package io.github.beombeom2.data.firstLaunch.dataSource

import kotlinx.coroutines.flow.Flow

interface FirstLaunchLocalDataSource {
    val isFirstLaunchFlow: Flow<Boolean>
    suspend fun setOnboardingSeen()
}