package com.teamsolply.solply.collection.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.collection.CollectionRoute
import com.teamsolply.solply.collection.CollectionViewModel
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateCollection(
    navOptions: NavOptions
) {
    navigate(Collection, navOptions)
}

fun NavGraphBuilder.collectionNavGraph(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    navigateToPlaceCollection: (Long, String) -> Unit,
    navigateToCourseCollection: (Long, String) -> Unit,
    navigateToPlace: () -> Unit,
    navigateToCourse: () -> Unit
) {
    composable<Collection> { backStackEntry ->
        val viewModel: CollectionViewModel = hiltViewModel(backStackEntry)
        CollectionRoute(
            paddingValues = paddingValues,
            navigateToBack = navigateToBack,
            navigateToPlaceCollection = navigateToPlaceCollection,
            navigateToCourseCollection = navigateToCourseCollection,
            navigateToPlace = navigateToPlace,
            navigateToCourse = navigateToCourse,
            viewModel = viewModel
        )
    }
}

@Serializable
data object Collection : Route
