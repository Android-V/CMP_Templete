package io.github.beombeom2.domain.firstLaunch.usecase

import io.github.beombeom2.domain.firstLaunch.repository.FirstLaunchRepository

class PostFirstLaunchUseCase (
    private val repo: FirstLaunchRepository
) {
    suspend operator fun invoke() = repo.setOnboardingSeen()
}