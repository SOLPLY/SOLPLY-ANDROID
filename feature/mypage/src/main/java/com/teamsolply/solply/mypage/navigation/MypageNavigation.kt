package com.teamsolply.solply.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.mypage.MypageRoute
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
    navigateToBack: () -> Unit
) {
    composable<Mypage> {
        MypageRoute(
            paddingValues = paddingValues,
            navigateToMaps = navigateToMaps,
            navigateToBack = navigateToBack
        )
    }
}

@Serializable
data object Mypage : Route
