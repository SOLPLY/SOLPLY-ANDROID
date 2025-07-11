package com.teamsolply.solply.place

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.place.model.PlaceData
import com.teamsolply.solply.place.model.PlaceTypeFilterItem
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
    ),
    val placeList: List<PlaceData> = immutableListOf(
        PlaceData(
            placeId = 1,
            placeName = "이수용바보",
            thumbnailUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.FOOD,
            isBookmarked = true
        ),
        PlaceData(
            placeId = 2,
            placeName = "연남동",
            thumbnailUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.CAFE,
            isBookmarked = false
        ),
        PlaceData(
            placeId = 3,
            placeName = "홍대어딘가",
            thumbnailUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.UNIQUE,
            isBookmarked = true
        ),
        PlaceData(
            placeId = 4,
            placeName = "하현상최고",
            thumbnailUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.WALK,
            isBookmarked = false
        ),
        PlaceData(
            placeId = 5,
            placeName = "크크르상회",
            thumbnailUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.CAFE,
            isBookmarked = true
        ),
        PlaceData(
            placeId = 6,
            placeName = "메롱2",
            thumbnailUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.CAFE,
            isBookmarked = true
        ),
        PlaceData(
            placeId = 7,
            placeName = "메롱3",
            thumbnailUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.CAFE,
            isBookmarked = true
        ),
        PlaceData(
            placeId = 8,
            placeName = "불꽃놀이",
            thumbnailUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.SHOPPING,
            isBookmarked = false
        ),
        PlaceData(
            placeId = 9,
            placeName = "연희동달팽이",
            thumbnailUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.BOOK,
            isBookmarked = false
        )
    ),
    val placeTypeFilterItems: List<PlaceTypeFilterItem> = listOf(
        PlaceTypeFilterItem("ALL", "전체", com.teamsolply.solply.designsystem.R.drawable.ic_all),
        PlaceTypeFilterItem("CAFE", "카페", com.teamsolply.solply.designsystem.R.drawable.ic_caffe),
        PlaceTypeFilterItem("FOOD", "음식", com.teamsolply.solply.designsystem.R.drawable.ic_food),
        PlaceTypeFilterItem("SHOPPING", "쇼핑", com.teamsolply.solply.designsystem.R.drawable.ic_shopping),
        PlaceTypeFilterItem("BOOK", "책방/서점", com.teamsolply.solply.designsystem.R.drawable.ic_book),
        PlaceTypeFilterItem("UNIQUE", "이색공간", com.teamsolply.solply.designsystem.R.drawable.ic_unique),
        PlaceTypeFilterItem("WALK", "산책", com.teamsolply.solply.designsystem.R.drawable.ic_walk)
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