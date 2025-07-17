package com.teamsolply.solply.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.topbar.SolplyTopBar
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.mypage.component.TabScreen
import com.teamsolply.solply.mypage.model.CourseTownEntity
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.PlaceTownEntity
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MypageRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    navigateToBack: () -> Unit,
    navigateToPlaceCollection: (Long, String) -> Unit,
    navigateToCourseCollection: (Long, String) -> Unit,
    navigateToPlace: () -> Unit,
    navigateToCourse: () -> Unit,
    viewModel: MypageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { 2 })

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == 0) {
            viewModel.sendIntent(MypageIntent.SelectPlaceTab)
        } else {
            viewModel.sendIntent(MypageIntent.SelectCourseTab)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getPlaceTownList()
        viewModel.getCourseTownList()
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is MypageSideEffect.NavigateToBack -> navigateToBack()
                is MypageSideEffect.NavigateToPlaceCollection -> {
                    navigateToPlaceCollection(sideEffect.townId, sideEffect.townName)
                }

                is MypageSideEffect.NavigateToCourseCollection -> {
                    navigateToCourseCollection(sideEffect.townId, sideEffect.townName)
                }

                is MypageSideEffect.NavigateToPLace -> {
                    navigateToPlace()
                }

                is MypageSideEffect.NavigateToCourse -> {
                    navigateToCourse()
                }
            }
        }
    }

    MypageScreen(
        navigateToMaps = navigateToMaps,
        onBackButtonClick = { viewModel.sendIntent(MypageIntent.BackButtonClick) },
        onEmptyButtonClick = { viewModel.sendIntent(MypageIntent.EmptyButtonClick(it)) },
        onClickPlaceTab = { viewModel.sendIntent(MypageIntent.SelectPlaceTab) },
        onClickCourseTab = { viewModel.sendIntent(MypageIntent.SelectCourseTab) },
        selectTown = { townId, townName ->
            if (pagerState.currentPage == 0) {
                viewModel.sendIntent(
                    MypageIntent.SelectPlaceTown(
                        townId = townId,
                        townName = townName
                    )
                )
            } else {
                viewModel.sendIntent(
                    MypageIntent.SelectCourseTown(
                        townId = townId,
                        townName = townName
                    )
                )
            }
        },
        pagerState = pagerState,
        placeTown = uiState.placeTowns,
        courseTown = uiState.courseTowns
    )
}

@Composable
fun MypageScreen(
    navigateToMaps: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    onEmptyButtonClick: (MypageTab) -> Unit,
    onClickPlaceTab: () -> Unit,
    onClickCourseTab: () -> Unit,
    selectTown: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(pageCount = { 2 }),
    placeTown: List<PlaceTownEntity>,
    courseTown: List<CourseTownEntity>
) {
    val coroutineScope = rememberCoroutineScope()
    val selectedIndex = pagerState.currentPage
    val list = listOf("장소", "코스")

    Column(
        modifier
            .fillMaxSize()
            .background(color = SolplyTheme.colors.white),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SolplyTopBar(
            barText = stringResource(R.string.mypage_collection),
            onBackButtonClick = { onBackButtonClick() }
        )
        TabRow(
            selectedTabIndex = 0,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                    color = SolplyTheme.colors.black
                )
            },
            containerColor = SolplyTheme.colors.white
        ) {
            list.forEachIndexed { index, tab ->
                val selected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 14.dp)
                        .customClickable(
                            rippleEnabled = false,
                            onClick = {
                                if (index == 0) {
                                    onClickPlaceTab()
                                } else {
                                    onClickCourseTab()
                                }
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        index
                                    )
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab,
                        style = if (selected) SolplyTheme.typography.head15Sb else SolplyTheme.typography.head15M,
                        color = if (selected) SolplyTheme.colors.black else SolplyTheme.colors.gray800
                    )
                }
            }
        }
        HorizontalPager(
            modifier = Modifier,
            state = pagerState
        ) { page ->
            when (page) {
                0 -> TabScreen(
                    onClickEmptyButton = onEmptyButtonClick,
                    placeTown = placeTown,
                    courseTown = courseTown,
                    onClickTown = selectTown,
                    mypageTab = MypageTab.PLACE
                )

                1 -> TabScreen(
                    onClickEmptyButton = onEmptyButtonClick,
                    placeTown = placeTown,
                    courseTown = courseTown,
                    onClickTown = selectTown,
                    mypageTab = MypageTab.COURSE
                )
            }
        }
        Text(
            text = "Mypage",
            modifier = Modifier.customClickable { navigateToMaps(MapsType.EDIT_COURSE.name) }
        )
    }
}
