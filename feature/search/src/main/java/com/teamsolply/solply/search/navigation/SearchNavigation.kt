package com.teamsolply.solply.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.navigation.Route
import com.teamsolply.solply.search.SearchDialog
import kotlinx.serialization.Serializable

fun NavController.navigateSearch(navOptions: NavOptions) {
    navigate(Search, navOptions)
}

fun NavGraphBuilder.searchNavGraph(
    paddingValues: PaddingValues,
    navigateToPlaceDetail: (String, Long, Long) -> Unit,
    navigateToRegisterPlace: () -> Unit,
    navigateToBack: () -> Unit
) {
    composable<Search> {
        SearchDialog(
            paddingValues = paddingValues,
            navigateToPlaceDetail = navigateToPlaceDetail,
            navigateToRegisterPlace = navigateToRegisterPlace,
            navigateToBack = navigateToBack
        )
    }
}

@Serializable
data object Search : Route