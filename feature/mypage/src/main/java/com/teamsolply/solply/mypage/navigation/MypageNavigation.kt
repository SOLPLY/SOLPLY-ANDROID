package com.teamsolply.solply.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.mypage.MypageRoute
import com.teamsolply.solply.mypage.MypageViewModel
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateMypage(
    navOptions: NavOptions
) {
    navigate(Mypage, navOptions)
}

fun NavGraphBuilder.mypageNavGraph(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    navigateToBack: () -> Unit,
    navigateToPlaceCollection: (Long, String) -> Unit,
    navigateToCourseCollection: (Long, String) -> Unit,
    navigateToPlace: () -> Unit,
    navigateToCourse: () -> Unit
) {
    composable<Mypage> { backStackEntry ->
        val viewModel: MypageViewModel = hiltViewModel(backStackEntry)
        MypageRoute(
            paddingValues = paddingValues,
            navigateToMaps = navigateToMaps,
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
data object Mypage : Route
