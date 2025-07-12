package com.teamsolply.solply.mypage.collection.place

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.card.SolplyPlaceCard
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.R
import com.teamsolply.solply.mypage.component.MypageTopBar
import com.teamsolply.solply.mypage.model.PlaceCard
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import com.teamsolply.solply.ui.preview.DefaultPreview
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PlaceCollectionRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    navigateToBack: () -> Unit,
    viewModel: PlaceCollectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.selectMode) {
        Log.d("select Mode", uiState.selectMode.toString())
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is PlaceCollectionSideEffect.NavigateToBack -> navigateToBack()
//                is PlaceCollectionSideEffect.NavigateToMap -> navigateToMaps()
            }
        }
    }

    PlaceCollectionScreen(
        town = uiState.town,
        place = uiState.places,
        onBackButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.BackButtonClick) },
        navigateToMaps = navigateToMaps,
        onSelectButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.SelectClick) },
        onCancelButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.CancelClick) },
        isSelectMode = uiState.selectMode
    )
}

@Composable
fun PlaceCollectionScreen(
    town: String,
    place: List<PlaceCard>,
    onBackButtonClick: () -> Unit,
    navigateToMaps: (String) -> Unit,
    onSelectButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit,
    isSelectMode: Boolean,
    modifier: Modifier = Modifier,
) {
    val selectText =
        if (isSelectMode) stringResource(R.string.mypage_delete) else stringResource(R.string.mypage_select)
    val cancelText = if (isSelectMode) stringResource(R.string.mypage_cancel) else ""
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MypageTopBar(
            town = town, // TODO 선택한 동 이름
            onBackButtonClick = { onBackButtonClick() },
            isTownSelected = true,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = cancelText,
                style = SolplyTheme.typography.button14R,
                color = SolplyTheme.colors.black,
                modifier = Modifier
                    .padding(start = 28.dp)
                    .then(
                        if (isSelectMode) {
                            Modifier.customClickable(
                                rippleEnabled = false
                            ) {
                                onCancelButtonClick()
                            }
                        } else {
                            Modifier
                        }
                    )
            )
            Text(
                text = selectText,
                style = SolplyTheme.typography.button14R,
                color = SolplyTheme.colors.black,
                modifier = Modifier
                    .padding(end = 28.dp)
                    .then(
                        if (isSelectMode) {
                            Modifier.customClickable(rippleEnabled = false) {
                                //TODO 삭제 기능
                            }
                        } else {
                            Modifier.customClickable(
                                rippleEnabled = false
                            ) {
                                onSelectButtonClick()
                            }
                        }
                    )
            )

        }
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(top = 16.dp, start = 17.dp, end = 17.dp)
        ) {
            itemsIndexed(place) { index, it ->
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = if (index % 2 == 0) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    }
                ) {
                    SolplyPlaceCard(
                        name = it.placeName,
                        placeType = it.placeType,
                        imgRes = it.imageUrls[0],
                        modifier =
                            if (index % 2 == 0) {
                                Modifier.padding(end = 5.dp)
                            } else {
                                Modifier.padding(start = 5.dp)
                            }
                    )
                }
            }
        }
    }
}


@DefaultPreview
@Composable
fun PlaceCollectionScreenPreview() {
    SolplyTheme {
        PlaceCollectionScreen(
            town = "연희동",
            place = emptyList(),
            onBackButtonClick = {},
            navigateToMaps = {},
            onSelectButtonClick = {},
            onCancelButtonClick = {},
            isSelectMode = false
        )
    }
}