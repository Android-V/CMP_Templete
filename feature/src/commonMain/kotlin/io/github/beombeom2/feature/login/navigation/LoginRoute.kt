package io.github.beombeom2.feature.login.navigation

import androidx.compose.runtime.Composable
import io.github.beombeom2.feature.login.ui.LoginScreen

@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
) {
    LoginScreen()
}