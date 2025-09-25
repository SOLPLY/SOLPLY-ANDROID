package com.teamsolply.solply.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.component.searchbar.SolplySearchbar
import com.teamsolply.solply.designsystem.component.topbar.SolplyTopBar
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.search.component.SearchItem
import com.teamsolply.solply.ui.extension.customClickable
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchRoute(
    paddingValues: PaddingValues,
    onBack: () -> Unit,
    navigateToPlaceDetail: (Long, Long) -> Unit,
    onNoPlaceClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { se ->
            when (se) {
                is SearchSideEffect.NavigateToPlaceDetail -> navigateToPlaceDetail(se.townId, se.placeId)
                SearchSideEffect.NavigateToNoResult -> onNoPlaceClick()
            }
        }
    }
    LaunchedEffect(Unit) { viewModel.sendIntent(SearchIntent.Retry) }

    SearchScreen(
        modifier = Modifier.padding(paddingValues),
        state = state,
        onBack = onBack,
        onQueryChanged = { viewModel.sendIntent(SearchIntent.QueryChanged(it)) },
        onClearQuery = { viewModel.sendIntent(SearchIntent.ClearQuery) },
        onSubmit = { viewModel.sendIntent(SearchIntent.Submit) },
        onClickItem = { id -> viewModel.sendIntent(SearchIntent.ClickItem(id)) },
        onClickNoResult = { viewModel.sendIntent(SearchIntent.ClickNoResult) }
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    onBack: () -> Unit,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    onSubmit: () -> Unit,
    onClickItem: (Long) -> Unit,
    onClickNoResult: () -> Unit
) {
    BackHandler(onBack = onBack)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 12.dp)
    ) {
        SolplyTopBar(
            barText = "검색하기",
            onBackButtonClick = onBack
        )

        SolplySearchbar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            query = state.query,
            onQueryChange = {
                onQueryChanged(it)
                if (it.isBlank()) {
                    onClearQuery()
                }
            },
            onImageClick = onSubmit
        )

        if (state.hasQuery) {
            Text(
                text = "검색 결과 ${state.resultCount}개",
                style = SolplyTheme.typography.button14M,
                color = SolplyTheme.colors.gray800,
                modifier = Modifier.padding(start = 20.dp, top = 32.dp, bottom = 16.dp)
            )
        }

        if (state.hasQuery) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = SolplyTheme.colors.gray200
                    )
                }

                val items = state.results.toImmutableList()
                itemsIndexed(items) { _, item ->
                    SearchItem(
                        placeName = item.name,
                        placeTag = item.tag,
                        placeAddress = item.address,
                        placeImageUrl = item.imageUrl,
                        onClick = { onClickItem(item.id) }
                    )
                }

                item {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = SolplyTheme.colors.gray200
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .customClickable(rippleEnabled = false) { onClickNoResult() }
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "찾는 장소가 없어요",
                            style = SolplyTheme.typography.button14M,
                            color = SolplyTheme.colors.gray700,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = com.teamsolply.solply.designsystem.R.drawable.ic_arrow_right_icon),
                            contentDescription = "arrow-right-icon",
                            tint = SolplyTheme.colors.gray700
                        )
                    }
                }

                item {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = SolplyTheme.colors.gray200
                    )
                }
            }
        }
    }
}
