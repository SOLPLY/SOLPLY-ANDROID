package com.teamsolply.solply.mypage.collection.place

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.mypage.R
import com.teamsolply.solply.mypage.collection.component.CollectionScreen
import com.teamsolply.solply.mypage.collection.component.SelectModeBar
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PlaceCollectionRoute(
    paddingValues: PaddingValues,
    townId: Long,
    townName: String,
    navigateToMaps: (String, Long, Long) -> Unit,
    navigateToBack: () -> Unit,
    viewModel: PlaceCollectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectText =
        if (uiState.selectMode) stringResource(R.string.mypage_delete) else stringResource(R.string.mypage_select)
    val cancelText = if (uiState.selectMode) stringResource(R.string.mypage_cancel) else ""

    LaunchedEffect(Unit) {
        viewModel.sendIntent(PlaceCollectionIntent.Init(townId, townName))
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is PlaceCollectionSideEffect.NavigateToBack -> navigateToBack()
                is PlaceCollectionSideEffect.NavigateToMap -> navigateToMaps(
                    MapsType.PLACE_DETAIL.name,
                    townId,
                    sideEffect.placeId
                )
            }
        }
    }

    CollectionScreen(
        town = uiState.townName,
        onBackButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.BackButtonClick) },
        onDialogConfirmClick = { viewModel.sendIntent(PlaceCollectionIntent.DialogConfirmClick) },
        onDialogDismissClick = { viewModel.sendIntent(PlaceCollectionIntent.DialogDismissClick) },
        content = {
            item(span = { GridItemSpan(maxLineSpan) }) {
                SelectModeBar(
                    selectMode = uiState.selectMode,
                    onSelectButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.SelectButtonClick) },
                    onDeleteButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.DeleteButtonClick) },
                    onCancelButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.CancelButtonClick) }
                )
            }
            itemsIndexed(uiState.places) { index, it ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .customClickable(
                            rippleEnabled = false
                        ) {
                            viewModel.sendIntent(
                                PlaceCollectionIntent.PlaceCardClick(
                                    it.placeId,
                                    index
                                )
                            )
                        },
                    contentAlignment = if (index % 2 == 0) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    }
                ) {
                    SolplyPlaceCard(
                        name = it.placeName,
                        placeType = it.placeType,
                        imgRes = it.imageUrls,
                        selected = it.isSelected,
                        saved = it.isSaved,
                        touchable = false,
                        modifier =
                        if (index % 2 == 0) {
                            Modifier.padding(end = 5.dp)
                        } else {
                            Modifier.padding(start = 5.dp)
                        }
                    )
                }
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(60.dp))
            }
        },
        dialogState = uiState.dialogState
    )
}
