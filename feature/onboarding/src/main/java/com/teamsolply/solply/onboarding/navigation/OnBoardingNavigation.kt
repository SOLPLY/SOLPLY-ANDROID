package com.teamsolply.solply.onboarding.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.navigation.Route
import com.teamsolply.solply.onboarding.OnBoardingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateOnBoarding(
    navOptions: NavOptions
) {
    navigate(OnBoarding, navOptions)
}

fun NavGraphBuilder.onBoardingNavGraph(
    navController: NavController,
    paddingValues: PaddingValues,
    navigateToPlace: () -> Unit,

    ) {
    composable<OnBoarding> {
        OnBoardingRoute(
            paddingValues = paddingValues,
            navigateToPlace = navigateToPlace,
            navController = navController
        )
    }
}

@Serializable
data object OnBoarding : Route
