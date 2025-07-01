package com.teamsolply.solply.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.teamsolply.solply.course.navigation.courseNavGraph
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.main.component.MainBottomBar
import com.teamsolply.solply.main.splash.splashNavGraph
import com.teamsolply.solply.maps.navigation.mapsNavGraph
import com.teamsolply.solply.mypage.navigation.mypageNavGraph
import com.teamsolply.solply.oauth.navigation.oauthNavGraph
import com.teamsolply.solply.onboarding.navigation.onBoardingNavGraph
import com.teamsolply.solply.place.navigation.placeNavGraph
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator = rememberMainNavigator()
) {
    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            NavHost(
                navController = navigator.navController,
                startDestination = navigator.startDestination,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                modifier = modifier
                    .background(color = SolplyTheme.colors.white)
                    .fillMaxSize()
            ) {
                splashNavGraph(
                    navigateToOauth = {
                        val navOptions = navOptions {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        navigator.navigateToOauth(navOptions = navOptions)
                    },
                    navigateToPlace = {
                        val navOptions = navOptions {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        navigator.navigateToPlace(navOptions = navOptions)
                    }
                )
                oauthNavGraph(
                    paddingValues = innerPadding,
                    navigateToOnBoarding = {
                        val navOptions = navOptions {
                            launchSingleTop = true
                        }
                        navigator.navigateToOnboarding(navOptions)
                    }
                )
                onBoardingNavGraph(
                    paddingValues = innerPadding,
                    navigateToPlace = {
                        val navOptions = navOptions {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        navigator.navigateToPlace(navOptions = navOptions)
                    }
                )
                placeNavGraph(
                    paddingValues = innerPadding,
                    navigateToMaps = { mapsType ->
                        val navOptions = navOptions {
                        }
                        navigator.navigateToMaps(mapsType = mapsType, navOptions = navOptions)
                    }
                )
                courseNavGraph(
                    paddingValues = innerPadding,
                    navigateToMaps = { mapsType ->
                        val navOptions = navOptions {
                        }
                        navigator.navigateToMaps(mapsType = mapsType, navOptions = navOptions)

                    })
                mypageNavGraph(
                    paddingValues = innerPadding,
                    navigateToMaps = { mapsType ->
                        val navOptions = navOptions {
                        }
                        navigator.navigateToMaps(mapsType = mapsType, navOptions = navOptions)
                    })
                mapsNavGraph(paddingValues = innerPadding)
            }
        },
        bottomBar = {
            MainBottomBar(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 8.dp, end = 8.dp, bottom = 10.dp),
                visible = navigator.setBottomBarVisibility(),
                tabs = MainNavTab.entries.toPersistentList(),
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) }
            )
        }
    )
}
