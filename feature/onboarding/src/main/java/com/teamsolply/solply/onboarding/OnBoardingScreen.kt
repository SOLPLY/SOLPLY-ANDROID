package com.teamsolply.solply.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.onboarding.component.BackHeader
import com.teamsolply.solply.onboarding.component.ProgressBar
import com.teamsolply.solply.onboarding.screen.AllowClauseScreen
import com.teamsolply.solply.onboarding.screen.NamingScreen
import com.teamsolply.solply.onboarding.screen.SelectPersonaScreen
import com.teamsolply.solply.onboarding.screen.SelectTownScreen
import com.teamsolply.solply.onboarding.screen.StartingScreen
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun OnBoardingRoute(
    paddingValues: PaddingValues,
    navigateToPlace: () -> Unit,
    navController: NavController,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is OnBoardingSideEffect.NavigateToPlace -> {
                    navigateToPlace()
                }
            }
        }
    }

    if (state.showStartingScreen) {
        StartingScreen(
            state = state,
            onFinished = {
                viewModel.sendIntent(OnBoardingIntent.OnBoardingButtonClick)
            }
        )
    } else {
        OnBoardingScreen(
            state = state,
            onBoardingButtonClick = {
                viewModel.sendIntent(OnBoardingIntent.ShowStartingScreen)
            },
            onPageChanged = { viewModel.sendIntent(OnBoardingIntent.OnPageChanged(it)) },
            onBoardingIntent = { viewModel.sendIntent(it) },
            navController = navController,
            changeInputNickname = { inputNickname ->
                viewModel.sendIntent(OnBoardingIntent.ChangeInputNickname(inputNickname))
            },
            checkOnBoardingSuccess = { isOnBoardingSuccess ->
                viewModel.sendIntent(OnBoardingIntent.ChangeOnBoardingSuccess(isOnBoardingSuccess))
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun OnBoardingScreen(
    state: OnBoardingState,
    onBoardingButtonClick: () -> Unit,
    onPageChanged: (Int) -> Unit,
    onBoardingIntent: (OnBoardingIntent) -> Unit,
    navController: NavController,
    changeInputNickname: (String) -> Unit,
    checkOnBoardingSuccess: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.totalPageCount }
    )

    val scope = rememberCoroutineScope()

    BackHandler(enabled = true) {
        if (pagerState.currentPage > 0) {
            scope.launch {
                pagerState.scrollToPage(pagerState.currentPage - 1)
            }
        } else {
            navController.popBackStack()
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SolplyTheme.colors.gray100)
    ) {
        Spacer(
            modifier = Modifier
                .height(32.dp)
        )
        BackHeader(
            barText = when (pagerState.currentPage) {
                0 -> ""
                1 -> ""
                2 -> ""
                3 -> ""
                else -> ""
            },
            onBackButtonClick = {
                if (pagerState.currentPage > 0) {
                    scope.launch {
                        pagerState.scrollToPage(pagerState.currentPage - 1)
                    }
                } else {
                    navController.popBackStack()
                }
            },
            isTownSelected = when (pagerState.currentPage) {
                0 -> state.selectedTownId != null
                1 -> state.selectedPersona != null
                2 -> state.selectedPersona != null
                3 -> state.userNickname.isNotBlank()
                else -> false
            }
        )

        ProgressBar(
            pageState = pagerState,
            totalpageCount = state.totalPageCount
        )

        HorizontalPager(
            pageSize = PageSize.Fill,
            state = pagerState,
            modifier = Modifier.weight(1f),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> AllowClauseScreen(
                    state = state,
                    onNextClick = {
                        scope.launch {
                            pagerState.scrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    onBoardingIntent = onBoardingIntent
                )

                1 -> SelectTownScreen(
                    state = state,
                    onNextClick = {
                        scope.launch {
                            pagerState.scrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    onBoardingIntent = onBoardingIntent
                )

                2 -> SelectPersonaScreen(
                    state = state,
                    onNextClick = {
                        scope.launch {
                            pagerState.scrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    onBoardingIntent = onBoardingIntent
                )

                3 -> NamingScreen(
                    state = state,
                    inputNickname = state.userNickname,
                    isNicknameDuplicate = state.isNicknameDuplicate,
                    changeInputNickname = changeInputNickname,
                    onNextClick = {
                        scope.launch {
                            pagerState.scrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    checkOnBoardingSuccess = checkOnBoardingSuccess,
                    onBoardingIntent = onBoardingIntent
                )
            }
        }
    }
}
