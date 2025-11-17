package com.teamsolply.solply.course.favoriteTown.favoriteTownNavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.teamsolply.solply.course.favoriteTown.FavoriteTownRoute
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateFavoriteTown(
    navOptions: NavOptions,
    selectedTownId: Long
) {
    navigate(FavoriteTown(selectedTownId = selectedTownId), navOptions)
}

fun NavGraphBuilder.favoriteTownNavGraph(
    paddingValues: PaddingValues,
    navigateToBack: (Long?) -> Unit
) {
    composable<FavoriteTown> { backStackEntry ->
        val selectedTownId = backStackEntry.toRoute<FavoriteTown>().selectedTownId
        FavoriteTownRoute(
            paddingValues = paddingValues,
            selectedTownId = selectedTownId,
            navigateToBack = navigateToBack
        )
    }
}

@Serializable
data class FavoriteTown(val selectedTownId: Long) : Route
