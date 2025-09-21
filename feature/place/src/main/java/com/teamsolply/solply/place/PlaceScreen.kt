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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.component.card.SolplyPlaceCard
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.model.PlaceSubType
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.place.component.bottomsheet.PlaceOptionFilterSheet
import com.teamsolply.solply.place.component.bottomsheet.PlaceTypeFilterSheet
import com.teamsolply.solply.place.component.button.PlaceChipButton
import com.teamsolply.solply.place.component.card.PlaceRecommendCard
import com.teamsolply.solply.place.component.header.PlaceHeader
import com.teamsolply.solply.place.model.PlaceData
import com.teamsolply.solply.place.model.RecommendPlaceInfo
import com.teamsolply.solply.place.model.TagEntity
import com.teamsolply.solply.place.util.LocationPermissionRequest
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import toPlaceType
import toPlaceTypeFilterItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String, Long, Long) -> Unit,
    navigateToTownSelect: () -> Unit,
    viewModel: PlaceViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is PlaceSideEffect.NavigateToMap -> {
                    navigateToMaps(
                        MapsType.PLACE_DETAIL.name,
                        state.userInfo.selectedTown.townId,
                        sideEffect.placeId
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.sendIntent(PlaceIntent.Retry)
    }

    LocationPermissionRequest()
    PlaceScreen(
        modifier = Modifier.padding(paddingValues),
        uiState = state,
        onPlaceClick = { viewModel.sendIntent(PlaceIntent.PlaceClicked(it)) },
        snackbarHostState = snackbarHostState,

        onClickMainFilterChip = {
            viewModel.sendIntent(PlaceIntent.MainFilterChipClick)
        },
        onClickSubFilterChip = {
            viewModel.sendIntent(PlaceIntent.SubFilterChipClick)
        },
        navigateToTownSelect = navigateToTownSelect
    )

    if (state.isMainFilterBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.sendIntent(PlaceIntent.MainFilterBottomSheetDismiss) },
            sheetState = sheetState,
            containerColor = SolplyTheme.colors.white,
            dragHandle = null
        ) {
            PlaceTypeFilterSheet(
                filterItems = state.mainFilterItems.map {
                    it.toPlaceType().toPlaceTypeFilterItem()
                },
                selectedType = state.selectedMainFilter,
                onSelectType = { mainFilterId, mainFilterName ->
                    viewModel.sendIntent(
                        PlaceIntent.MainFilterClick(
                            mainFilterId,
                            mainFilterName
                        )
                    )
                },
                onDismiss = {
                    viewModel.sendIntent(PlaceIntent.MainFilterBottomSheetDismiss)
                }
            )
        }
    }

    if (state.isOptionFilterBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.sendIntent(PlaceIntent.SubFilterBottomSheetDismiss)
            },
            sheetState = sheetState,
            containerColor = SolplyTheme.colors.white,
            dragHandle = null
        ) {
            PlaceOptionFilterSheet(
                optionTags = state.subFilterItems ?: emptyList(),
                selectedOptionIds = state.selectedOptionAFilter + state.selectedOptionBFilter,
                onOptionSelected = { tagId, tagType ->
                    viewModel.sendIntent(
                        PlaceIntent.SubFilterClick(
                            optionFilterId = tagId,
                            selectedTagType = tagType
                        )
                    )
                },
                onDismiss = {
                    viewModel.sendIntent(PlaceIntent.SubFilterBottomSheetDismiss)
                },
                onReset = { viewModel.sendIntent(PlaceIntent.SubFilterBottomSheetResetButtonClick) },
                onDone = {
                    viewModel.sendIntent(PlaceIntent.SubFilterBottomSheetCompleteButtonClick)
                }
            )
        }
    }
}

@Composable
fun PlaceScreen(
    modifier: Modifier = Modifier,
    uiState: PlaceState,
    onPlaceClick: (Long) -> Unit,
    snackbarHostState: SnackbarHostState,

    onClickMainFilterChip: () -> Unit,
    onClickSubFilterChip: () -> Unit,

    navigateToTownSelect: () -> Unit

) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val centerItemSize = 240.dp
    val sideItemSize = 180.dp
    val pagerSize = uiState.recommendPlaces.size.coerceAtLeast(1)
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2 - ((Int.MAX_VALUE / 2) % pagerSize),
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
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        PlaceHeader(
            townName = uiState.userInfo.selectedTown.townName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClickTownName = { navigateToTownSelect() }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = getRecommendText(uiState.userInfo.persona, uiState.userInfo.nickname),
                    style = SolplyTheme.typography.display20Sb,
                    modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 28.dp)
                )
            }
            item(span = { GridItemSpan(2) }) {
                if (uiState.recommendPlaces.isNotEmpty()) {
                    CustomHorizontalPager(
                        pagerState = pagerState,
                        recommendPlaces = uiState.recommendPlaces,
                        centerItemSize = centerItemSize,
                        horizontalPadding = horizontalPadding,
                        isCenter = isCenter,
                        page1ItemSize = page1ItemSize,
                        page2ItemSize = page2ItemSize,
                        page3ItemSize = page3ItemSize,
                        onPlaceClick = onPlaceClick
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(centerItemSize),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "추천 장소가 없습니다.",
                            style = SolplyTheme.typography.display16Sb,
                            color = SolplyTheme.colors.gray400
                        )
                    }
                }
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(21.dp))
            }
            item(span = { GridItemSpan(2) }) {
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
                            PlaceChipButton(
                                text = PlaceType.valueOf(uiState.selectedMainFilter).displayName,
                                modifier = Modifier,
                                onClick = { onClickMainFilterChip() }
                            )

                            if (!uiState.subFilterItems.isNullOrEmpty() && uiState.selectedMainFilter != "ALL") {
                                val optionFilterText = getOptionFilterText(
                                    selectedIds = uiState.selectedOptionAFilter + uiState.selectedOptionBFilter,
                                    optionTags = uiState.subFilterItems
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                PlaceChipButton(
                                    text = optionFilterText,
                                    modifier = Modifier,
                                    onClick = {
                                        onClickSubFilterChip()
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        when (uiState.placesLoadState) {
                            is PlacesLoadState.Loading -> {
                                repeat(6) {
                                    if (it % 2 == 0) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                                        ) {
                                            repeat(2) {
                                                Box(
                                                    modifier = Modifier.weight(1f)
                                                ) {
                                                    PlaceSkeletonCard()
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(14.dp))
                                    }
                                }
                            }

                            is PlacesLoadState.Success -> {
                                val chunkedList = uiState.placesLoadState.placeList.chunked(2)
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
}

@Composable
fun PlaceGridItem(
    place: PlaceData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SolplyPlaceCard(
        name = place.placeName,
        imgRes = place.thumbnailUrl,
        placeType = place.primaryTag,
        modifier = modifier,
        onClick = onClick,
        saved = place.isBookmarked
    )
}

@Composable
fun CustomHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    recommendPlaces: List<RecommendPlaceInfo>,
    centerItemSize: Dp = 240.dp,
    horizontalPadding: Dp = 0.dp,
    isCenter: MutableList<Boolean>,
    page1ItemSize: State<Dp>,
    page2ItemSize: State<Dp>,
    page3ItemSize: State<Dp>,
    onPlaceClick: (Long) -> Unit
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

            val itemSize = when (page % recommendPlaces.size) {
                0 -> page1ItemSize.value
                1 -> page2ItemSize.value
                else -> page3ItemSize.value
            }
            val scale = if (itemSize == 240.dp) 1f else 0.75f

            PlaceRecommendCard(
                title = place.placeName,
                subtitle = place.introduction,
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
                scale = scale,
                onClick = { onPlaceClick(place.placeId) }
            )
        }
    }
}

private fun getOptionFilterText(
    selectedIds: List<Int>,
    optionTags: List<TagEntity>
): String {
    if (selectedIds.isEmpty()) return "추가옵션"

    val minTagId = selectedIds.minOrNull()
    val firstTagName = optionTags.find { it.tagId == minTagId }?.let { tag ->
        try {
            PlaceSubType.valueOf(tag.name).displayName
        } catch (e: Exception) {
            tag.name
        }
    } ?: "추가옵션"

    return if (selectedIds.size == 1) {
        firstTagName
    } else {
        "$firstTagName 외 ${selectedIds.size - 1}개"
    }
}

fun getRecommendText(persona: String, nickname: String): String {
    return when (persona) {
        "REST" -> "조용히 사색을 즐기는\n${nickname}님을 위한 오늘의 추천"
        "EXPLORER" -> "골목 곳곳을 탐색하는\n${nickname}님을 위한 오늘의 추천"
        "MOODING" -> "취향을 모으는\n${nickname}님을 위한 오늘의 추천"
        "NATURAL" -> "힐링이 필요한\n${nickname}님을 위한 오늘의 추천"
        else -> "솔플리가 추천하는\n${nickname}님을 위한 오늘의 추천"
    }
}
