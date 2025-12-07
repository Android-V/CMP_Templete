package io.github.beombeom2.feature.onBoarding.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.beombeom2.feature.onBoarding.ui.OnboardingScreen
import io.github.beombeom2.feature.onBoarding.ui.OnboardingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingRoute(
    navigateToLogin: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OnboardingScreen(
        state = uiState,
        onNextClick = { viewModel.onEvent(OnboardingUiEvent.NextClicked) },
        onGetStartedClick = {
            viewModel.onEvent(OnboardingUiEvent.GetStartedClicked)
            navigateToLogin()
        }
    )
}