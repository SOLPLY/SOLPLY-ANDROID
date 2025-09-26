package com.teamsolply.solply.mypage.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.mypage.MypageRoute
import com.teamsolply.solply.mypage.MypageViewModel
import com.teamsolply.solply.mypage.profile.ProfileRoute
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateProfile(
    navOptions: NavOptions
) {
    navigate(Profile, navOptions)
}

fun NavGraphBuilder.profileNavGraph(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    navigateToMypage: () -> Unit,
) {
    composable<Profile> { backStackEntry ->
        val viewModel: MypageViewModel = hiltViewModel(backStackEntry)
        ProfileRoute(
            paddingValues = paddingValues,
            navigateToBack = navigateToBack,
            navigateToMypage = navigateToMypage,
            viewModel = viewModel
        )
    }
}

@Serializable
data object Profile : Route