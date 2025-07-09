package com.teamsolply.solply.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
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
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.card.SolplyPlaceCard
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.mypage.component.MypageTopBar
import com.teamsolply.solply.mypage.model.PlaceCard
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.preview.DefaultPreview
import kotlinx.coroutines.launch

@Composable
fun MypageRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    viewModel: MypageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MypageScreen(
        navigateToMaps = navigateToMaps,
        place = uiState.places
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MypageScreen(
    navigateToMaps: (String) -> Unit,
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
            onBackButtonClick = {}
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
                Tab(
                    selected = selected,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    modifier = Modifier.padding(vertical = 14.dp)
                ) {
                    Text(
                        text = page,
                        style = SolplyTheme.typography.head15Sb,
                        color = SolplyTheme.colors.black
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
            place = emptyList()
        )
    }
}

@Composable
private fun PlaceCollectionScreen(
    onClickEmptyButton: () -> Unit,
    place: List<PlaceCard>,
    modifier: Modifier = Modifier,
    isEmpty: Boolean = true
) {
    if (isEmpty) {
        EmptyCollectionScreen(
            onClick = onClickEmptyButton
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
//            items(place) {
//                SolplyPlaceCard(
//                    name = it.placeName,
//                    placeType = it.placeType,
//                    imgRes = it.imageUrls[0],
//                    iconColor = SolplyTheme.colors.black,
//                    iconBackGroundColor = SolplyTheme.colors.white
//                )
//            }
        }
    }
}

@Composable
private fun EmptyCollectionScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "저장한 장소가 없어요.",
            style = SolplyTheme.typography.body16M,
            color = SolplyTheme.colors.gray800
        )
        Spacer(
            modifier = Modifier
                .height(28.dp)
        )
        SolplyBasicButton(
            text = "나만의 장소 수집하러 가기",
            onClick = onClick,
            textColor = SolplyTheme.colors.purple700,
            enabledBackgroundColor = SolplyTheme.colors.purple300,
            modifier = Modifier.padding(horizontal = 64.dp)
        )
    }
}

@DefaultPreview
@Composable
private fun PlaceCollectionScreenPreview() {
    SolplyTheme {
        PlaceCollectionScreen(
            onClickEmptyButton = {},
            place = emptyList()
        )
    }
}