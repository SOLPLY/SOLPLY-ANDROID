package com.teamsolply.solply.mypage.collection.place

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class PlaceCollectionState(
    val selectMode: Boolean = false,
    val town: String = "연희동",
    val places: List<PlaceInfoEntity> = immutableListOf(
        PlaceInfoEntity(
            placeId = 0,
            placeName = "0번",
            placeType = PlaceType.CAFE,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceInfoEntity(
            placeId = 1,
            placeName = "1번",
            placeType = PlaceType.BOOK,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceInfoEntity(
            placeId = 2,
            placeName = "2번",
            placeType = PlaceType.SHOPPING,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceInfoEntity(
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceInfoEntity(
            placeId = 4,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceInfoEntity(
            placeId = 5,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceInfoEntity(
            placeId = 6,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceInfoEntity(
            placeId = 7,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceInfoEntity(
            placeId = 8,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        )
    ),
    val selectedPlaces: Set<Int> = emptySet(),
    val dialogState: Boolean = false
) : UiState

sealed interface PlaceCollectionIntent : UiIntent {

    data object SelectButtonClick : PlaceCollectionIntent
    data object DeleteButtonClick : PlaceCollectionIntent
    data object CancelButtonClick : PlaceCollectionIntent

    data object DialogConfirmClick : PlaceCollectionIntent
    data object DialogDismissClick : PlaceCollectionIntent

    data class PlaceCardClick(val placeId: Int, val index: Int) : PlaceCollectionIntent

    // Navigate
    data object BackButtonClick : PlaceCollectionIntent
}

sealed interface PlaceCollectionSideEffect : SideEffect {

    data object DeletePlaces : PlaceCollectionSideEffect

    data object NavigateToBack : PlaceCollectionSideEffect
    data object NavigateToMap : PlaceCollectionSideEffect
}
