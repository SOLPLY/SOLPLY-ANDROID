package com.teamsolply.solply.place

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.component.card.SolplyPlaceCard
import com.teamsolply.solply.designsystem.component.header.PlaceHeader
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.place.component.button.PlaceChipButton
import com.teamsolply.solply.place.component.card.PlaceRecommendCard
import com.teamsolply.solply.place.model.PlaceData
import com.teamsolply.solply.place.model.RecommendPlaceInfo
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.teamsolply.solply.place.component.button.FilterSheetButton
import com.teamsolply.solply.place.model.PlaceTypeFilterItem

@Composable
fun PlaceRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    viewModel: PlaceViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            if (sideEffect == PlaceSideEffect.NavigateToMap) navigateToMaps("")
        }
    }
    PlaceScreen(
        modifier = Modifier.padding(paddingValues),
        state = state,
        onPlaceClick = { viewModel.sendIntent(PlaceIntent.PlaceClicked(it)) },
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceScreen(
    modifier: Modifier = Modifier,
    state: PlaceState,
    onPlaceClick: (Int) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val centerItemSize = 240.dp
    val sideItemSize = 180.dp
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2 - ((Int.MAX_VALUE / 2) % state.recommendplaces.size),
        pageCount = { Int.MAX_VALUE }
    )
    val isCenter = remember { mutableStateListOf(false, false, false) }
    val horizontalPadding = (screenWidth - centerItemSize) / 2 + (centerItemSize - sideItemSize) / 2
    val page1ItemSize = animateDpAsState(
        targetValue = if (isCenter[0]) 240.dp else 180.dp
    )
    val page2ItemSize = animateDpAsState(
        targetValue = if (isCenter[1]) 240.dp else 180.dp
    )
    val page3ItemSize = animateDpAsState(
        targetValue = if (isCenter[2]) 240.dp else 180.dp
    )

    val showFilterSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val selectedType = remember { mutableStateOf("ALL") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PlaceHeader(
            townName = state.user.favoriteTowns,
            persona = state.user.persona,
            nickname = state.user.nickname,
            modifier = Modifier.fillMaxWidth()
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp, top = 0.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = when (state.user.persona) {
                        "HEALING" -> "조용히 사색을 즐기는\n${state.user.nickname}님을 위한 오늘의 추천"
                        "EXPLORER" -> "골목 곳곳을 탐색하는\n${state.user.nickname}님을 위한 오늘의 추천"
                        "MOODING" -> "취향을 모으는\n${state.user.nickname}님을 위한 오늘의 추천"
                        "NATURAL" -> "힐링이 필요한\n${state.user.nickname}님을 위한 오늘의 추천"
                        else -> "솔플리가 추천하는\n${state.user.nickname}님을 위한 오늘의 추천"
                    },
                    style = SolplyTheme.typography.display20Sb,
                    modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 28.dp)
                )
            }
            item(span = { GridItemSpan(2) }) {
                CustomHorizontalPager(
                    pagerState = pagerState,
                    recommendPlaces = state.recommendplaces,
                    centerItemSize = centerItemSize,
                    horizontalPadding = horizontalPadding,
                    isCenter = isCenter,
                    page1ItemSize = page1ItemSize,
                    page2ItemSize = page2ItemSize,
                    page3ItemSize = page3ItemSize,
                    onPlaceClick = onPlaceClick
                )
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(21.dp))
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(21.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(SolplyTheme.colors.white)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 💡 버튼 클릭 시 상태 변경만
                            PlaceChipButton(
                                text = "전체",
                                modifier = Modifier,
                                onClick = { showFilterSheet.value = true }
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        val chunkedList = state.placeList.chunked(2)
                        chunkedList.forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                for (place in rowItems) {
                                    Box(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        PlaceGridItem(
                                            place = place,
                                            onClick = { onPlaceClick(place.placeId) }
                                        )
                                    }
                                }
                                repeat(2 - rowItems.size) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    }
                }
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(60.dp))
            }
            item(span = { GridItemSpan(2) }) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }

    if (showFilterSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet.value = false },
            sheetState = sheetState,
            containerColor = SolplyTheme.colors.white,
            dragHandle = null
        ) {
            PlaceTypeFilterSheet(
                filterItems = state.placeTypeFilterItems,
                selectedType = selectedType.value,
                onSelectType = {
                    selectedType.value = it
                    showFilterSheet.value = false
                    // TODO: 실제 필터링 동작 연결
                },
                onDismiss = { showFilterSheet.value = false }
            )
        }
    }
}


@Composable
fun PlaceGridItem(
    place: PlaceData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SolplyPlaceCard(
        name = place.placeName,
        imgRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
        placeType = place.primaryTag,
        modifier = modifier,
        onClick = onClick,
        saved = place.isBookmarked
    )
}

@Composable
fun CustomHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: androidx.compose.foundation.pager.PagerState,
    recommendPlaces: List<RecommendPlaceInfo>,
    centerItemSize: Dp = 240.dp,
    horizontalPadding: Dp = 0.dp,
    isCenter: MutableList<Boolean>,
    page1ItemSize: State<Dp>,
    page2ItemSize: State<Dp>,
    page3ItemSize: State<Dp>,
    onPlaceClick: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(centerItemSize)
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 46.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(centerItemSize),
            contentPadding = PaddingValues(horizontal = horizontalPadding)
        ) { page ->
            val place = recommendPlaces[page % recommendPlaces.size]
            isCenter[page % recommendPlaces.size] = page == pagerState.currentPage

            PlaceRecommendCard(
                title = place.placeName,
                subtitle = place.description,
                type = place.primaryTag,
                imgRes = place.thumbnailImageUrl,
                modifier = Modifier
                    .requiredWidth(
                        when (page % recommendPlaces.size) {
                            0 -> page1ItemSize.value
                            1 -> page2ItemSize.value
                            else -> page3ItemSize.value
                        }
                    )
                    .requiredHeight(
                        when (page % recommendPlaces.size) {
                            0 -> page1ItemSize.value
                            1 -> page2ItemSize.value
                            else -> page3ItemSize.value
                        }
                    ),
                onClick = { onPlaceClick(place.placeId) }
            )
        }
    }
}

@Composable
fun PlaceTypeFilterSheet(
    filterItems: List<PlaceTypeFilterItem>,
    selectedType: String,
    onSelectType: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 20.dp,),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "장소 유형",
                style = SolplyTheme.typography.display20Sb,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_close),
                contentDescription = "close",
                modifier = Modifier.size(24.dp).customClickable { onDismiss() }
            )
        }
        filterItems.forEachIndexed { idx, item ->
            FilterSheetButton (
                iconRes = item.iconRes,
                label = item.label,
                selected = selectedType == item.type,
                showCheck = selectedType == item.type,
                onClick = { onSelectType(item.type) }
            )
            if (idx < filterItems.size - 1) {
                HorizontalDivider(thickness = 1.dp, color = SolplyTheme.colors.gray100)
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}