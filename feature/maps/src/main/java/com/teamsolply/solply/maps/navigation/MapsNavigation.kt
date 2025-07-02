package com.teamsolply.solply.maps.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.teamsolply.solply.maps.MapsRoute
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateMaps(
    mapsType: String,
    navOptions: NavOptions
) {
    navigate(Maps(mapsType = mapsType), navOptions)
}

fun NavGraphBuilder.mapsNavGraph(
    paddingValues: PaddingValues,
    navigateToPlaceDetail: () -> Unit,
    navigateToPlace: () -> Unit,
    navigateToCourse: () -> Unit,
    navigateToMypage: () -> Unit,

    ) {
    composable<Maps> { backStackEntry ->
        val mapsType = MapsType.valueOf(backStackEntry.toRoute<Maps>().mapsType)
        MapsRoute(
            mapsType = mapsType,
            navigatePlaceDetail = navigateToPlaceDetail,
            navigateToPlace = navigateToPlace,
            navigateToCourse = navigateToCourse,
            navigateToMypage = navigateToMypage,
            paddingValues = paddingValues
        )
    }
}

@Serializable
data class Maps(
    val mapsType: String
) : Route
