package io.github.beombeom2.cmp

import Splash
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.github.beombeom2.feature.login.navigation.Login
import io.github.beombeom2.feature.login.navigation.loginNavGraph
import io.github.beombeom2.feature.onBoarding.navigation.Onboarding
import io.github.beombeom2.feature.onBoarding.navigation.onboardingNavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import splashNavGraph

@Composable
@Preview
fun App() {
    MaterialTheme {
        Surface {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Splash
            ) {
                splashNavGraph(
                    navigateToOnboarding = {
                        navController.navigate(Onboarding) {
                            popUpTo(Splash) { inclusive = true }
                        }
                    },
                    navigateToLogin = {
                        navController.navigate(Login) {
                            popUpTo(Splash) { inclusive = true }
                        }
                    }
                )

                onboardingNavGraph(
                    navigateToLogin = {
                        navController.navigate(Login) {
                            popUpTo(Onboarding) { inclusive = true }
                        }
                    }
                )

                loginNavGraph(
                    navigateToHome = {
                        // TODO: Home 라우트 정의 후 여기서 이동
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AppPreview() {
    App()
}