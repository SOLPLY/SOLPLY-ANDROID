package com.teamsolply.solply.mypage.collection.place

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.PlaceCard
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class PlaceCollectionState(
    val selectMode: Boolean = false,
    val town: String = "연희동",
    val places: List<PlaceCard> = immutableListOf(
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
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceCard(
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceCard(
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceCard(
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceCard(
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        )
    ),
) : UiState

sealed interface PlaceCollectionIntent : UiIntent {
    data object SelectClick : PlaceCollectionIntent
    data object CancelClick : PlaceCollectionIntent

    // Navigate
    data object BackButtonClick : PlaceCollectionIntent
}

sealed interface PlaceCollectionSideEffect : SideEffect {
    data object NavigateToBack : PlaceCollectionSideEffect
//    data object NavigateToMap(val ) : PlaceCollectionSideEffect
}