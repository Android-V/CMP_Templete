package io.github.beombeom2.data.firstLaunch.repository

import io.github.beombeom2.data.firstLaunch.dataSource.FirstLaunchLocalDataSource
import io.github.beombeom2.domain.firstLaunch.repository.FirstLaunchRepository
import kotlinx.coroutines.flow.Flow

class FirstLaunchRepositoryImpl(
    private val local: FirstLaunchLocalDataSource
) : FirstLaunchRepository {
    override val isFirstLaunchFlow: Flow<Boolean> = local.isFirstLaunchFlow
    override suspend fun setOnboardingSeen() = local.setOnboardingSeen()
}