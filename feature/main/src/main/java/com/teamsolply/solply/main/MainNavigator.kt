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
import com.teamsolply.solply.course.navigation.Course
import com.teamsolply.solply.course.navigation.navigateCourse
import com.teamsolply.solply.main.splash.Splash
import com.teamsolply.solply.maps.navigation.navigateMaps
import com.teamsolply.solply.mypage.navigation.navigateMypage
import com.teamsolply.solply.oauth.navigation.navigateOauth
import com.teamsolply.solply.onboarding.navigation.navigateOnBoarding
import com.teamsolply.solply.place.navigation.Place
import com.teamsolply.solply.place.navigation.navigatePlace

internal class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Splash

    val currentTab: MainNavTab?
        @Composable get() = MainNavTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainNavTab) {
        when (tab) {
            MainNavTab.PLACE -> {
                val navOptions = navOptions {
                    popUpTo(0) {
                        inclusive = false
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                navController.navigatePlace(navOptions)
            }

            MainNavTab.COURSE -> {
                val navOptions = navOptions {
                    popUpTo(0) {
                        inclusive = false
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                navController.navigateCourse(navOptions)
            }

            MainNavTab.MYPAGE -> {
                val navOptions = navOptions {
                    launchSingleTop = true
                    restoreState = true
                }
                navController.navigateMypage(navOptions)
            }
        }
    }

    private fun createTabNavOptions() = navOptions {
        popUpTo(navController.graph.id) {
            inclusive = false
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
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

    fun navigateToCourse(navOptions: NavOptions) {
        navController.navigateCourse(navOptions)
    }

    fun navigateToMypage(navOptions: NavOptions) {
        navController.navigateMypage(navOptions)
    }

    fun navigateToMaps(
        mapsType: String,
        navOptions: NavOptions
    ) {
        navController.navigateMaps(
            mapsType = mapsType,
            navOptions = navOptions
        )
    }

    fun navigateToBack() {
        navController.popBackStack()
    }

    @Composable
    fun setBottomBarVisibility(): Boolean {
        return MainNavTab.entries
            .filterNot { it == MainNavTab.MYPAGE }
            .any { currentDestination?.hasRoute(it.route::class) == true }
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
