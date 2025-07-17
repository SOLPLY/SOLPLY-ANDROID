package com.teamsolply.solply.mypage.collection.place

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigatePlaceCollection(
    townId: Long,
    townName: String,
    navOptions: NavOptions
) {
    navigate(PlaceCollection(townId = townId, townName = townName), navOptions)
}

fun NavGraphBuilder.placeCollectionNavGraph(
    paddingValues: PaddingValues,
    navigateToMaps: (String, Long, Long) -> Unit,
    navigateToBack: () -> Unit
) {
    composable<PlaceCollection> { backStackEntry ->
        PlaceCollectionRoute(
            paddingValues = paddingValues,
            townId = backStackEntry.toRoute<PlaceCollection>().townId,
            townName = backStackEntry.toRoute<PlaceCollection>().townName,
            navigateToMaps = navigateToMaps,
            navigateToBack = navigateToBack
        )
    }
}

@Serializable
data class PlaceCollection(val townId: Long, val townName: String) : Route
