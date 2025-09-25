package com.teamsolply.solply.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.navigation.Route
import com.teamsolply.solply.search.SearchRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSearch(navOptions: NavOptions) {
    navigate(Search, navOptions)
}

fun NavGraphBuilder.searchNavGraph(
    paddingValues: PaddingValues,
    onBack: () -> Unit,
    navigateToPlaceDetail: (Long, Long) -> Unit,
    onNoPlaceClick: () -> Unit
) {
    composable<Search> {
        SearchRoute(
            paddingValues = paddingValues,
            onBack = onBack,
            navigateToPlaceDetail = navigateToPlaceDetail,
            onNoPlaceClick = onNoPlaceClick
        )
    }
}

@Serializable
data object Search : Route
