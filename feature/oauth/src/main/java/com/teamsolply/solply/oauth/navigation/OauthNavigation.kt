package com.teamsolply.solply.oauth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.navigation.Route
import com.teamsolply.solply.oauth.OauthRoute
import kotlinx.serialization.Serializable

fun NavController.navigateOauth(
    navOptions: NavOptions
) {
    navigate(Oauth, navOptions)
}

fun NavGraphBuilder.oauthNavGraph(
    paddingValues: PaddingValues,
    navigateToOnBoarding: () -> Unit,
    navigateToPlace: () -> Unit
) {
    composable<Oauth> {
        OauthRoute(
            paddingValues = paddingValues,
            navigateToOnBoarding = navigateToOnBoarding,
            navigateToPlace = navigateToPlace
        )
    }
}

@Serializable
data object Oauth : Route
