package com.teamsolply.solply.mypage.withdraw.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.mypage.withdraw.WithdrawRoute
import com.teamsolply.solply.mypage.withdraw.WithdrawViewModel
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateWithdraw(
    navOptions: NavOptions
) {
    navigate(Withdraw, navOptions)
}

fun NavGraphBuilder.withdrawNavGraph(
    navigateToBack: () -> Unit,
    navigateToOauth: () -> Unit,
    paddingValues: PaddingValues
) {
    composable<Withdraw> { backStackEntry ->
        val viewModel: WithdrawViewModel = hiltViewModel(backStackEntry)
        WithdrawRoute(
            paddingValues = paddingValues,
            navigateToBack = navigateToBack,
            navigateToOauth = navigateToOauth,
            viewModel = viewModel
        )
    }
}

@Serializable
data object Withdraw : Route
