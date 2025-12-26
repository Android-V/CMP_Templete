package io.github.beombeom2.domain.firstLaunch.usecase

import io.github.beombeom2.domain.firstLaunch.repository.FirstLaunchRepository
import kotlinx.coroutines.flow.Flow

class GetFirstLaunchUseCase (
    private val repo: FirstLaunchRepository
    ) {
        operator fun invoke(): Flow<Boolean> = repo.isFirstLaunchFlow
    }