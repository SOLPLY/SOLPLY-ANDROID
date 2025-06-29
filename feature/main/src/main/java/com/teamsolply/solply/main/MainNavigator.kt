package com.teamsolply.solply.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.teamsolply.solply.course.navigation.navigateCourse
import com.teamsolply.solply.maps.navigation.Maps
import com.teamsolply.solply.oauth.navigation.navigateOauth
import com.teamsolply.solply.onboarding.navigation.navigateOnBoarding
import com.teamsolply.solply.place.navigation.navigatePlace

internal class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Maps

    val currentTab: MainNavTab?
        @Composable get() = MainNavTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainNavTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) {
                inclusive = false
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainNavTab.PLACE -> navController.navigatePlace(navOptions)
            MainNavTab.COURSE -> navController.navigateCourse(navOptions)
        }
    }

    fun navigateToOauth(navOptions: NavOptions) {
        navController.navigateOauth(navOptions)
    }

    fun navigateToOnboarding(navOptions: NavOptions) {
        navController.navigateOnBoarding(navOptions)
    }

    fun navigateToPlace(navOptions: NavOptions) {
        navController.navigatePlace(navOptions)
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
