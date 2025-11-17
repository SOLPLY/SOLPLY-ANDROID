package com.teamsolply.solply.registerplace.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.navigation.Route
import com.teamsolply.solply.registerplace.RegisterPlaceRoute
import kotlinx.serialization.Serializable

fun NavController.navigateRegisterPlace(navOptions: NavOptions) {
    navigate(RegisterPlace, navOptions)
}

fun NavGraphBuilder.registerPlaceNavGraph(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit
) {
    composable<RegisterPlace> {
        RegisterPlaceRoute(
            paddingValues = paddingValues,
            navigateToBack = navigateToBack
        )
    }
}

@Serializable
data object RegisterPlace : Route