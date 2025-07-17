package com.teamsolply.solply.mypage.collection.place

import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class PlaceCollectionState(
    val selectMode: Boolean = false,
    val townId: Long = 0,
    val townName: String = "",
    val places: List<PlaceInfoEntity> = emptyList(),
    val selectedPlaces: Set<Long> = emptySet(),
    val dialogState: Boolean = false
) : UiState

sealed interface PlaceCollectionIntent : UiIntent {
    data class Init(val townId: Long, val townName: String) : PlaceCollectionIntent

    data object SelectButtonClick : PlaceCollectionIntent
    data object DeleteButtonClick : PlaceCollectionIntent
    data object CancelButtonClick : PlaceCollectionIntent

    data object DialogConfirmClick : PlaceCollectionIntent
    data object DialogDismissClick : PlaceCollectionIntent

    data class PlaceCardClick(val placeId: Long, val index: Int) : PlaceCollectionIntent

    // Navigate
    data object BackButtonClick : PlaceCollectionIntent
}

sealed interface PlaceCollectionSideEffect : SideEffect {

    data object NavigateToBack : PlaceCollectionSideEffect
    data class NavigateToMap(
        val placeId: Long
    ) : PlaceCollectionSideEffect
}
