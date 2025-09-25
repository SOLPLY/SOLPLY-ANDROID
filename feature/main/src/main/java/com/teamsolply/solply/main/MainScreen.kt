package com.teamsolply.solply.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.teamsolply.solply.collection.collection.course.courseCollectionNavGraph
import com.teamsolply.solply.collection.collection.place.placeCollectionNavGraph
import com.teamsolply.solply.collection.navigation.collectionNavGraph
import com.teamsolply.solply.course.favoriteTown.favoriteTownNavigation.favoriteTownNavGraph
import com.teamsolply.solply.course.navigation.courseNavGraph
import com.teamsolply.solply.designsystem.component.snackbar.SolplyNavigateSimpleSnackBar
import com.teamsolply.solply.designsystem.component.snackbar.SolplyNavigateSnackBar
import com.teamsolply.solply.designsystem.component.snackbar.SolplyNotificationSnackBar
import com.teamsolply.solply.designsystem.component.snackbar.SolplyTextSnackBar
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.main.component.MainBottomBar
import com.teamsolply.solply.main.model.SolplySnackBarData
import com.teamsolply.solply.main.splash.splashNavGraph
import com.teamsolply.solply.maps.navigation.mapsNavGraph
import com.teamsolply.solply.model.SnackBarType
import com.teamsolply.solply.mypage.navigation.mypageNavGraph
import com.teamsolply.solply.oauth.navigation.oauthNavGraph
import com.teamsolply.solply.onboarding.navigation.onBoardingNavGraph
import com.teamsolply.solply.place.navigation.placeNavGraph
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator = rememberMainNavigator()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val currentSnackbarJob = remember { mutableStateOf<Job?>(null) }
    val currentSnackbarState = remember { mutableStateOf(SolplySnackBarData()) }

    suspend fun showTextSnackBar(message: String) {
        currentSnackbarJob.value?.join()
        currentSnackbarJob.value = coroutineScope.launch {
            currentSnackbarState.value = SolplySnackBarData(type = SnackBarType.TEXT, action = null)
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    suspend fun showNotificationSnackBar(message: String) {
        currentSnackbarJob.value?.join()
        currentSnackbarJob.value = coroutineScope.launch {
            currentSnackbarState.value =
                SolplySnackBarData(type = SnackBarType.NOTIFICATION, action = null)
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    suspend fun showNavigateSnackBar(message: String, onAction: () -> Unit) {
        currentSnackbarJob.value?.join()
        currentSnackbarJob.value = coroutineScope.launch {
            currentSnackbarState.value =
                SolplySnackBarData(type = SnackBarType.NAVIGATE, action = onAction)
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    suspend fun showNavigateSimpleSnackBar(message: String, onAction: () -> Unit) {
        currentSnackbarJob.value?.join()
        currentSnackbarJob.value = coroutineScope.launch {
            currentSnackbarState.value =
                SolplySnackBarData(type = SnackBarType.NAVIGATE_SIMPLE, action = onAction)
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            val layoutDirection = LocalLayoutDirection.current
            val mapsPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(layoutDirection),
                top = 0.dp,
                end = innerPadding.calculateEndPadding(layoutDirection),
                bottom = innerPadding.calculateBottomPadding()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                NavHost(
                    navController = navigator.navController,
                    startDestination = navigator.startDestination,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                    modifier = modifier
                        .background(color = SolplyTheme.colors.gray100)
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
                    onBoardingNavGraph(
                        navController = navigator.navController,
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
                        navigateToMaps = { mapsType, townId, placeId ->
                            val navOptions = navOptions {}
                            navigator.navigateToMaps(
                                mapsType = mapsType,
                                townId = townId,
                                placeId = placeId,
                                navOptions = navOptions
                            )
                        },
                        navigateToTownSelect = {
                            navigator.navigateToFavoriteTown()
                        }
                    )
                    courseNavGraph(
                        paddingValues = innerPadding,
                        navigateToMaps = { mapsType, townId, courseId ->
                            val navOptions = navOptions {}
                            navigator.navigateToMaps(
                                mapsType = mapsType,
                                townId = townId,
                                courseId = courseId,
                                navOptions = navOptions
                            )
                        },
                        navigateToTownSelect = {
                            navigator.navigateToFavoriteTown()
                        }
                    )
                    collectionNavGraph(
                        paddingValues = innerPadding,
                        navigateToBack = navigator::navigateToBack,
                        navigateToPlaceCollection = { townId, townName ->
                            val navOptions = navOptions { }
                            navigator.navigateToPlaceCollection(
                                townId = townId,
                                townName = townName,
                                navOptions = navOptions
                            )
                        },
                        navigateToCourseCollection = { townId, townName ->
                            val navOptions = navOptions { }
                            navigator.navigateToCourseCollection(
                                townId = townId,
                                townName = townName,
                                navOptions = navOptions
                            )
                        },
                        navigateToPlace = {
                            val navOptions = navOptions {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToPlace(
                                navOptions = navOptions
                            )
                        },
                        navigateToCourse = {
                            val navOptions = navOptions {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToCourse(
                                navOptions = navOptions
                            )
                        }
                    )
                    mapsNavGraph(
                        paddingValues = mapsPadding,
                        showTextSnackBar = { message ->
                            coroutineScope.launch {
                                showTextSnackBar(message)
                            }
                        },
                        showNotificationSnackBar = { message ->
                            coroutineScope.launch {
                                showNotificationSnackBar(message)
                            }
                        },
                        showNavigateSnackBar = { message, action ->
                            coroutineScope.launch {
                                showNavigateSnackBar(message, action)
                            }
                        },
                        showNavigateSimpleSnackBar = { message, action ->
                            coroutineScope.launch {
                                showNavigateSimpleSnackBar(message, action)
                            }
                        },
                        navigateToPlace = {
                            val navOptions = navOptions {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToPlace(navOptions = navOptions)
                        },
                        navigateToCourse = {
                            val navOptions = navOptions {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToCourse(navOptions = navOptions)
                        },
                        navigateToMap = { mapsType, townId, placeId, courseId ->
                            val navOptions = navOptions {
                            }
                            navigator.navigateToMaps(
                                mapsType = mapsType,
                                townId = townId,
                                placeId = placeId,
                                courseId = courseId,
                                navOptions = navOptions
                            )
                        },
                        navigateToBack = navigator::navigateToBack
                    )
                    placeCollectionNavGraph(
                        paddingValues = innerPadding,
                        navigateToMaps = { mapsType, townId, placeId ->
                            val navOptions = navOptions {}
                            navigator.navigateToMaps(
                                mapsType = mapsType,
                                townId = townId,
                                placeId = placeId,
                                navOptions = navOptions
                            )
                        },
                        navigateToBack = navigator::navigateToBack
                    )
                    courseCollectionNavGraph(
                        paddingValues = innerPadding,
                        navigateToMaps = { mapsType, townId, courseId ->
                            val navOptions = navOptions { }
                            navigator.navigateToMaps(
                                mapsType = mapsType,
                                townId = townId,
                                courseId = courseId,
                                navOptions = navOptions
                            )
                        },
                        navigateToBack = navigator::navigateToBack
                    )
                    mypageNavGraph(
                        paddingValues = innerPadding,
                        navigateToBack = navigator::navigateToBack
                    )
                    favoriteTownNavGraph(
                        paddingValues = innerPadding,
                        navigateToBack = navigator::navigateToBack
                    )
                }
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
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 32.dp),
                snackbar = { snackbarData ->
                    when (currentSnackbarState.value.type) {
                        SnackBarType.TEXT -> {
                            SolplyTextSnackBar(text = snackbarData.visuals.message)
                        }

                        SnackBarType.NOTIFICATION -> {
                            SolplyNotificationSnackBar(text = snackbarData.visuals.message)
                        }

                        SnackBarType.NAVIGATE -> {
                            SolplyNavigateSnackBar(
                                text = snackbarData.visuals.message,
                                navigateToRoute = {
                                    currentSnackbarState.value.action?.invoke()
                                }
                            )
                        }

                        SnackBarType.NAVIGATE_SIMPLE -> {
                            SolplyNavigateSimpleSnackBar(
                                navigateToRoute = {
                                    currentSnackbarState.value.action?.invoke()
                                }
                            )
                        }
                    }
                }
            )
        }
    )
}
