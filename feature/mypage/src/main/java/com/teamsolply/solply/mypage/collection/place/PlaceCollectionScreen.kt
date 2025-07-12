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
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.R
import com.teamsolply.solply.designsystem.component.dialog.SolplyConfirmDialog
import com.teamsolply.solply.mypage.component.MypageTopBar
import com.teamsolply.solply.mypage.model.PlaceCard
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import com.teamsolply.solply.ui.preview.DefaultPreview
import kotlinx.coroutines.flow.collectLatest
import okhttp3.internal.immutableListOf

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
                is PlaceCollectionSideEffect.NavigateToMap -> navigateToMaps(MapsType.PLACE_DETAIL.name)
                is PlaceCollectionSideEffect.DeletePlaces -> {
                    Log.d("selected Places", uiState.selectedPlaces.joinToString(","))
                    // TODO 장소 리스트 조회 api
                }

            }
        }
    }

    PlaceCollectionScreen(
        town = uiState.town,
        place = uiState.places,
        onBackButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.BackButtonClick) },
        onSelectButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.SelectButtonClick) },
        onDeleteButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.DeleteButtonClick) },
        onCancelButtonClick = { viewModel.sendIntent(PlaceCollectionIntent.CancelButtonClick) },
        onPlaceClick = { id, index ->
            viewModel.sendIntent(PlaceCollectionIntent.PlaceCardClick(id, index))
        },
        onDialogConfirmClick = { viewModel.sendIntent(PlaceCollectionIntent.DialogConfirmClick) },
        onDialogDismissClick = { viewModel.sendIntent(PlaceCollectionIntent.DialogDismissClick) },
        isSelectMode = uiState.selectMode,
        dialogState = uiState.dialogState
    )
}

@Composable
fun PlaceCollectionScreen(
    town: String,
    place: List<PlaceCard>,
    onBackButtonClick: () -> Unit,
    onSelectButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit,
    onPlaceClick: (Int, Int) -> Unit,
    onDialogConfirmClick: () -> Unit,
    onDialogDismissClick: () -> Unit,
    isSelectMode: Boolean,
    dialogState: Boolean,
    modifier: Modifier = Modifier,
) {
    val selectText =
        if (isSelectMode) stringResource(R.string.mypage_delete) else stringResource(R.string.mypage_select)
    val cancelText = if (isSelectMode) stringResource(R.string.mypage_cancel) else ""

    if (dialogState) {
        SolplyConfirmDialog(
            text = "선택한 장소를 삭제할까요?",
            confirmButtonText = stringResource(R.string.mypage_delete),
            dismissButtonText = stringResource(R.string.mypage_cancel),
            onClickConfirm = onDialogConfirmClick,
            onClickDismiss = onDialogDismissClick
        )
    }
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
                        Modifier.customClickable(rippleEnabled = false) {
                            if (isSelectMode) {
                                //TODO 삭제 기능
                                onDeleteButtonClick()
                            } else {
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
                        .fillMaxSize()
                        .customClickable(
                            rippleEnabled = false
                        ) {
                            onPlaceClick(it.placeId, index)
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
                        imgRes = it.imageUrls[0],
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
        }
    }
}


@DefaultPreview
@Composable
fun PlaceCollectionScreenPreview() {
    SolplyTheme {
        PlaceCollectionScreen(
            town = "연희동",
            place = immutableListOf(
                PlaceCard(
                    placeId = 0,
                    placeName = "0번",
                    placeType = PlaceType.CAFE,
                    imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
                ),
                PlaceCard(
                    placeId = 1,
                    placeName = "1번",
                    placeType = PlaceType.BOOK,
                    imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
                ),
                PlaceCard(
                    placeId = 2,
                    placeName = "2번",
                    placeType = PlaceType.SHOPPING,
                    imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
                ),
                PlaceCard(
                    placeId = 3,
                    placeName = "3번",
                    placeType = PlaceType.FOOD,
                    imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
                ),
                PlaceCard(
                    placeId = 4,
                    placeName = "3번",
                    placeType = PlaceType.FOOD,
                    imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
                ),
                PlaceCard(
                    placeId = 5,
                    placeName = "3번",
                    placeType = PlaceType.FOOD,
                    imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
                )
            ),
            onBackButtonClick = {},
            onSelectButtonClick = {},
            onDeleteButtonClick = {},
            onCancelButtonClick = {},
            onPlaceClick = { id, index -> {} },
            onDialogConfirmClick = {},
            onDialogDismissClick = {},
            isSelectMode = false,
            dialogState = false
        )
    }
}