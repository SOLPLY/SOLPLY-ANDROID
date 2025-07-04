package com.teamsolply.solply.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.teamsolply.solply.course.navigation.Course
import com.teamsolply.solply.course.navigation.courseNavGraph
import com.teamsolply.solply.designsystem.component.snackbar.SolplyNavigateSnackBar
import com.teamsolply.solply.designsystem.component.snackbar.SolplyNotificationSnackBar
import com.teamsolply.solply.designsystem.component.snackbar.SolplyTextSnackBar
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.main.component.MainBottomBar
import com.teamsolply.solply.main.splash.splashNavGraph
import com.teamsolply.solply.maps.navigation.mapsNavGraph
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.mypage.navigation.Mypage
import com.teamsolply.solply.mypage.navigation.mypageNavGraph
import com.teamsolply.solply.oauth.navigation.oauthNavGraph
import com.teamsolply.solply.onboarding.navigation.onBoardingNavGraph
import com.teamsolply.solply.place.navigation.Place
import com.teamsolply.solply.place.navigation.placeNavGraph
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator = rememberMainNavigator()
) {
    val coroutineScope = rememberCoroutineScope()

    val textSnackBarHostState = remember { SnackbarHostState() }
    val notificationSnackBarHostState = remember { SnackbarHostState() }
    val navigateSnackBarHostState = remember { SnackbarHostState() }

    data class SnackbarWithAction(
        val message: String,
        val action: () -> Unit
    )

    val snackbarWithActionState = remember { mutableStateOf<SnackbarWithAction?>(null) }

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
                    showSnackBar = { message, action ->
                        coroutineScope.launch {
                            snackbarWithActionState.value = SnackbarWithAction(message, action)
                            navigateSnackBarHostState.showSnackbar(message = message)
                        }
                    },
                    navigateToMaps = { mapsType ->
                        val navOptions = navOptions {
                        }
                        navigator.navigateToMaps(mapsType = mapsType, navOptions = navOptions)
                    },
                )
                courseNavGraph(
                    paddingValues = innerPadding,
                    navigateToMaps = { mapsType ->
                        val navOptions = navOptions {
                        }
                        navigator.navigateToMaps(mapsType = mapsType, navOptions = navOptions)
                    }
                )
                mypageNavGraph(
                    paddingValues = innerPadding,
                    navigateToMaps = { mapsType ->
                        val navOptions = navOptions {}
                        navigator.navigateToMaps(mapsType = mapsType, navOptions = navOptions)
                    }
                )
                mapsNavGraph(
                    paddingValues = innerPadding,
                    navigateToPlaceDetail = {
                        val navOptions = navOptions {}
                        navigator.navigateToMaps(
                            mapsType = MapsType.PLACE_DETAIL.name,
                            navOptions = navOptions
                        )
                    },
                    navigateToPlace = {
                        val navOptions = navOptions {
                            popUpTo(Place) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        navigator.navigateToPlace(navOptions = navOptions)
                    },
                    navigateToCourse = {
                        val navOptions = navOptions {
                            popUpTo(Course) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        navigator.navigateToCourse(navOptions = navOptions)
                    },
                    navigateToMypage = {
                        val navOptions = navOptions {
                            popUpTo(Mypage) {
                                inclusive = true
                            }
                        }
                        navigator.navigateToMypage(navOptions = navOptions)
                    },
                    navigateToBack = navigator::navigateToBack
                )
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
        },
        snackbarHost = {
            SnackbarHost(
                hostState = textSnackBarHostState,
                modifier = Modifier.padding(horizontal = 16.dp),
                snackbar = { snackbarData ->
                    SolplyTextSnackBar(text = snackbarData.visuals.message)
                }
            )
            SnackbarHost(
                hostState = notificationSnackBarHostState,
                modifier = Modifier.padding(horizontal = 16.dp),
                snackbar = { snackbarData ->
                    SolplyNotificationSnackBar(text = snackbarData.visuals.message)
                }
            )
            SnackbarHost(
                hostState = navigateSnackBarHostState,
                modifier = Modifier.padding(horizontal = 16.dp),
                snackbar = {
                    snackbarWithActionState.value?.let { data ->
                        SolplyNavigateSnackBar(
                            text = data.message,
                            navigateToRoute = data.action
                        )
                    }
                }
            )
        }
    )
}
