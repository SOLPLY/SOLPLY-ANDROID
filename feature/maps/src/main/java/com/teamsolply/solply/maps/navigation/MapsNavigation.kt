package com.teamsolply.solply.maps.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.maps.MapsRoute
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateMaps(
    navOptions: NavOptions
) {
    navigate(Maps, navOptions)
}

fun NavGraphBuilder.mapsNavGraph(
    paddingValues: PaddingValues
) {
    composable<Maps> {
        MapsRoute(
            paddingValues = paddingValues
        )
    }
}

@Serializable
data object Maps : Route
