package com.teamsolply.solply.main.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateSplash(
    navOptions: NavOptions
) {
    navigate(Splash, navOptions)
}

fun NavGraphBuilder.splashNavGraph(
    navigateToOauth: () -> Unit,
    navigateToPlace: () -> Unit
) {
    composable<Splash> {
        SplashScreen(
            navigateToOauth = navigateToOauth,
            navigateToPlace = navigateToPlace,
        )
    }
}

@Serializable
data object Splash : Route