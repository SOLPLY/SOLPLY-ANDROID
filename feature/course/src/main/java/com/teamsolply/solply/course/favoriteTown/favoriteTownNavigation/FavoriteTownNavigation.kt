package com.teamsolply.solply.course.favoriteTown.favoriteTownNavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.course.favoriteTown.FavoriteTownRoute
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateFavoriteTown(
    navOptions: NavOptions
) {
    navigate(FavoriteTown, navOptions)
}

fun NavGraphBuilder.favoriteTownNavGraph(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit
) {
    composable<FavoriteTown> {
        FavoriteTownRoute(
            paddingValues = paddingValues,
            navigateToBack = navigateToBack,
            onBoardingIntent = { }
        )
    }
}

@Serializable
data object FavoriteTown : Route
