package com.teamsolply.solply.place

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.place.model.PlaceUser
import com.teamsolply.solply.place.model.RecommendPlaceInfo
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class PlaceState(
    val user: PlaceUser = PlaceUser(
        userId = 0,
        nickname = "숭이",
        favoriteTowns = "연희동",
        persona = "HEALING",
    ),

    val recommendplaces: List<RecommendPlaceInfo> = immutableListOf(
        RecommendPlaceInfo(
            placeId = 0,
            placeName = "장소 이름",
            thumbnailImageUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.CAFE,
            description = "장소 한 줄 소개 장소 한 줄 소개"
        ),
        RecommendPlaceInfo(
            placeId = 1,
            placeName = "장소 이름",
            thumbnailImageUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.FOOD,
            description = "장소 한 줄 소개 장소 한 줄 소개"
        ),
        RecommendPlaceInfo(
            placeId = 2,
            placeName = "장소 이름",
            thumbnailImageUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.UNIQUE,
            description = "장소 한 줄 소개 장소 한 줄 소개 두 줄이 되어도 괜찮음음음음음음"
        ),
    )

) : UiState

sealed interface PlaceIntent : UiIntent {
    object LoadPlaces : PlaceIntent
    data class PlaceClicked(val placeId: Int) : PlaceIntent
    object Retry : PlaceIntent
}

sealed interface PlaceSideEffect : SideEffect {
    data object NavigateToMap : PlaceSideEffect
}