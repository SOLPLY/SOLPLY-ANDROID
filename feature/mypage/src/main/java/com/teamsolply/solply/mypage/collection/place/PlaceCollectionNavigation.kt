package com.teamsolply.solply.mypage.collection.place

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.mypage.MypageViewModel
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigatePlaceCollection(
    navOptions: NavOptions
) {
    navigate(PlaceCollection, navOptions)
}

fun NavGraphBuilder.placeCollectionNavGraph(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    navigateToBack: () -> Unit
) {
    composable<PlaceCollection> {
        PlaceCollectionRoute(
            paddingValues = paddingValues,
            navigateToMaps = navigateToMaps,
            navigateToBack = navigateToBack
        )
    }
}

@Serializable
data object PlaceCollection : Route