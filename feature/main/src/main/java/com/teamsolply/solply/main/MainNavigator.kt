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
import com.teamsolply.solply.collection.collection.course.navigateCourseCollection
import com.teamsolply.solply.collection.collection.place.navigatePlaceCollection
import com.teamsolply.solply.collection.navigation.navigateCollection
import com.teamsolply.solply.course.favoriteTown.favoriteTownNavigation.navigateFavoriteTown
import com.teamsolply.solply.course.navigation.navigateCourse
import com.teamsolply.solply.main.splash.Splash
import com.teamsolply.solply.maps.navigation.navigateMaps
import com.teamsolply.solply.mypage.navigation.navigateMypage
import com.teamsolply.solply.mypage.profile.navigation.navigateProfile
import com.teamsolply.solply.oauth.navigation.navigateOauth
import com.teamsolply.solply.onboarding.navigation.navigateOnBoarding
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

            MainNavTab.COLLECTION -> {
                val navOptions = navOptions {
                    launchSingleTop = true
                }
                navController.navigateCollection(navOptions)
            }

            MainNavTab.MYPAGE -> {
                val navOptions = navOptions {
                    launchSingleTop = true
                }
                navController.navigateMypage(navOptions)
            }
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

    fun navigateToCourse(navOptions: NavOptions) {
        navController.navigateCourse(navOptions)
    }

    fun navigateToMaps(
        mapsType: String,
        townId: Long,
        placeId: Long? = null,
        courseId: Long? = null,
        navOptions: NavOptions
    ) {
        navController.navigateMaps(
            mapsType = mapsType,
            townId = townId,
            placeId = placeId,
            courseId = courseId,
            navOptions = navOptions
        )
    }

    fun navigateToBack() {
        navController.popBackStack()
    }

    fun navigateToPlaceCollection(
        townId: Long,
        townName: String,
        navOptions: NavOptions
    ) {
        navController.navigatePlaceCollection(
            townId = townId,
            townName = townName,
            navOptions = navOptions
        )
    }

    fun navigateToCourseCollection(
        townId: Long,
        townName: String,
        navOptions: NavOptions
    ) {
        navController.navigateCourseCollection(
            townId = townId,
            townName = townName,
            navOptions
        )
    }

    fun navigateToMypage(
        navOptions: NavOptions
    ) {
        navController.navigateMypage(
            navOptions = navOptions
        )
    }

    fun navigateToProfile(
        navOptions: NavOptions
    ) {
        navController.navigateProfile(
            navOptions = navOptions
        )
    }

    fun navigateToFavoriteTown(navOptions: NavOptions = navOptions {}) {
        navController.navigateFavoriteTown(navOptions)
    }

    @Composable
    fun setBottomBarVisibility(): Boolean {
        return currentTab == MainNavTab.PLACE || currentTab == MainNavTab.COURSE
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
