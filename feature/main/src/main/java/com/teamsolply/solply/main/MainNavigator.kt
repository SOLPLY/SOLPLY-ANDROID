package com.teamsolply.solply.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.teamsolply.solply.home.navigation.navigateHome
import com.teamsolply.solply.oauth.navigation.Oauth

internal class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Oauth

    val currentTab: MainNavTab?
        @Composable get() = MainNavTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainNavTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainNavTab.HOME -> navController.navigateHome(navOptions)
            MainNavTab.DummyRoute1 -> navController.navigateHome(navOptions)
            MainNavTab.DummyRoute2 -> navController.navigateHome(navOptions)
            MainNavTab.DummyRoute3 -> navController.navigateHome(navOptions)
            MainNavTab.DummyRoute4 -> navController.navigateHome(navOptions)
        }
    }

    @Composable
    fun setBottomBarVisibility() = MainNavTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
