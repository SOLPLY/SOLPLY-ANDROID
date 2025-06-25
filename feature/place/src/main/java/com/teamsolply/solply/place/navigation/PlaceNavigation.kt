package com.teamsolply.solply.place.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.navigation.MainTabRoute
import com.teamsolply.solply.place.PlaceRoute
import kotlinx.serialization.Serializable

fun NavController.navigatePlace(
    navOptions: NavOptions
) {
    navigate(Place, navOptions)
}

fun NavGraphBuilder.placeNavGraph(
    paddingValues: PaddingValues
) {
    composable<Place> {
        PlaceRoute(
            paddingValues = paddingValues
        )
    }
}

@Serializable
data object Place : MainTabRoute