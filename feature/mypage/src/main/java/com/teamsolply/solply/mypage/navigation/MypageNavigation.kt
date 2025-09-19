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
    navigateToBack: () -> Unit,
) {
    composable<Mypage> { backStackEntry ->
        val viewModel: MypageViewModel = hiltViewModel(backStackEntry)
        MypageRoute(
            paddingValues = paddingValues,
            navigateToBack = navigateToBack,
            viewModel = viewModel
        )
    }
}

@Serializable
data object Mypage : Route
