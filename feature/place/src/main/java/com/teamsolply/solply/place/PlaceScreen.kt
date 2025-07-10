package com.teamsolply.solply.place

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.component.header.PlaceHeader
import com.teamsolply.solply.place.component.card.PlaceRecommendCard
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

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

    val horizontalPadding = (screenWidth - centerItemSize) / 2 + (centerItemSize - sideItemSize) / 2
    Column(modifier = modifier.fillMaxSize()) {
        PlaceHeader(
            townName = state.user.favoriteTowns,
            persona = state.user.persona,
            nickname = state.user.nickname,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(28.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 46.dp,
                modifier = Modifier
                    .height(centerItemSize)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = horizontalPadding)
            ) { page ->
                val place = state.recommendplaces[page % state.recommendplaces.size]
                val isCenter = page == pagerState.currentPage

                PlaceRecommendCard(
                    title = place.placeName,
                    subtitle = place.description,
                    type = place.primaryTag,
                    imgRes = place.thumbnailImageUrl,
                    modifier = Modifier
                        .requiredWidth(if (isCenter) centerItemSize else sideItemSize)
                        .requiredHeight(if (isCenter) centerItemSize else sideItemSize),
                    onClick = { onPlaceClick(place.placeId) }
                )

            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
