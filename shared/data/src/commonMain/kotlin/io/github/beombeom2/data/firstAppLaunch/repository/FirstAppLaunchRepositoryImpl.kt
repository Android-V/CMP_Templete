package io.github.beombeom2.data.firstAppLaunch.repository

import io.github.beombeom2.data.firstAppLaunch.dataSource.FirstAppLaunchLocalDataSource
import io.github.beombeom2.domain.firstAppLaunch.repository.FirstAppLaunchRepository
import kotlinx.coroutines.flow.Flow

class FirstAppLaunchRepositoryImpl(
    private val local: FirstAppLaunchLocalDataSource
) : FirstAppLaunchRepository {
    override val isFirstLaunchFlow: Flow<Boolean> = local.isFirstLaunchFlow
    override suspend fun setOnboardingSeen() = local.setOnboardingSeen()
}