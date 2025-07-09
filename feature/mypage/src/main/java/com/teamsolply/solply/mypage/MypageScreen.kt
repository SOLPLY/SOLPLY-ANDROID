package com.teamsolply.solply.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.mypage.component.MypageTopBar
import com.teamsolply.solply.mypage.component.PlaceCollectionScreen
import com.teamsolply.solply.mypage.model.PlaceCard
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import com.teamsolply.solply.ui.preview.DefaultPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MypageRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    navigateToBack: () -> Unit,
    viewModel: MypageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                MypageSideEffect.NavigateToBack -> navigateToBack()
            }
        }
    }

    MypageScreen(
        navigateToMaps = navigateToMaps,
        navigateToBack = { viewModel.sendIntent(MypageIntent.BackButtonClick) },
        place = uiState.places
    )
}

@Composable
fun MypageScreen(
    navigateToMaps: (String) -> Unit,
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    place: List<PlaceCard>
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val selectedIndex = pagerState.currentPage
    val list = listOf("장소", "코스")

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MypageTopBar(
            title = "수집함",
            onBackButtonClick = { navigateToBack() }
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
            list.forEachIndexed { index, page ->
                val selected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 14.dp)
                        .customClickable(
                            rippleEnabled = false,
                            onClick = {
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
                        text = page,
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
                0 -> PlaceCollectionScreen(
                    onClickEmptyButton = {},
                    place = place
                )
            }
        }
        Text(
            text = "Mypage",
            modifier = Modifier.customClickable { navigateToMaps(MapsType.EDIT_COURSE.name) }
        )
    }
}


@DefaultPreview
@Composable
private fun MypageScreenPreview() {
    SolplyTheme {
        MypageScreen(
            navigateToMaps = {},
            place = emptyList(),
            navigateToBack = {}
        )
    }
}