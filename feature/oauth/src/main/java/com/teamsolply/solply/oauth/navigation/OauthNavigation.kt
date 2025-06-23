package com.teamsolply.solply.oauth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.navigation.Route
import com.teamsolply.solply.oauth.presentation.OauthRoute
import kotlinx.serialization.Serializable

fun NavController.navigateOauth(
    navOptions: NavOptions
) {
    navigate(Oauth, navOptions)
}

fun NavGraphBuilder.oauthNavGraph(
    paddingValues: PaddingValues
) {
    composable<Oauth> {
        OauthRoute(
            paddingValues = paddingValues
        )
    }
}

@Serializable
data object Oauth : Route