package com.teamsolply.solply.mypage.collection.place

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.card.SolplyPlaceCard
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.mypage.collection.component.CollectionScreen
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

    LaunchedEffect(Unit) {
        viewModel.sendIntent(PlaceCollectionIntent.Init(townId, townName))
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is PlaceCollectionSideEffect.NavigateToBack -> navigateToBack()
                is PlaceCollectionSideEffect.NavigateToMap -> navigateToMaps(MapsType.PLACE_DETAIL.name, townId, sideEffect.placeId)
            }
        }
    }

    CollectionScreen(
        town = uiState.townName,
        onBackButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.BackButtonClick) },
        onSelectButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.SelectButtonClick) },
        onDeleteButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.DeleteButtonClick) },
        onCancelButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.CancelButtonClick) },
        onDialogConfirmClick = { viewModel.sendIntent(PlaceCollectionIntent.DialogConfirmClick) },
        onDialogDismissClick = { viewModel.sendIntent(PlaceCollectionIntent.DialogDismissClick) },
        isSelectMode = uiState.selectMode,
        content = {
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
        },
        dialogState = uiState.dialogState
    )
}
