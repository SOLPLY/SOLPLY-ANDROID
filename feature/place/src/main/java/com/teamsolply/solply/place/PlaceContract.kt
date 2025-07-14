package com.teamsolply.solply.place

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.place.model.PlaceData
import com.teamsolply.solply.place.model.PlaceTypeFilterItem
import com.teamsolply.solply.place.model.PlaceUser
import com.teamsolply.solply.place.model.RecommendPlaceInfo
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import okhttp3.internal.immutableListOf

data class OptionTag(
    val tagId: Int,
    val tagType: String,
    val name: String,
    val parentId: Int?
)

data class PlaceState(
    val user: PlaceUser = PlaceUser(
        userId = 0,
        nickname = "숭이",
        favoriteTowns = "연희동",
        persona = "HEALING",
    ),

    val recommendPlaces: List<RecommendPlaceInfo> = immutableListOf(
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
            placeName = "바보",
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
        PlaceTypeFilterItem(
            "SHOPPING",
            "쇼핑",
            com.teamsolply.solply.designsystem.R.drawable.ic_shopping
        ),
        PlaceTypeFilterItem("BOOK", "책방/서점", com.teamsolply.solply.designsystem.R.drawable.ic_book),
        PlaceTypeFilterItem(
            "UNIQUE",
            "이색공간",
            com.teamsolply.solply.designsystem.R.drawable.ic_unique
        ),
        PlaceTypeFilterItem("WALK", "산책", com.teamsolply.solply.designsystem.R.drawable.ic_walk)
    ),
    val optionTags: List<OptionTag>? = listOf(
        OptionTag(tagId = 1, tagType = "OPTION1", name = "독서", parentId = 1),
        OptionTag(tagId = 2, tagType = "OPTION1", name = "작업", parentId = 1),
        OptionTag(tagId = 3, tagType = "OPTION1", name = "커피/디저트", parentId = 1),
        OptionTag(tagId = 4, tagType = "OPTION1", name = "힐링", parentId = 1),
        OptionTag(tagId = 5, tagType = "OPTION2", name = "시그니처 메뉴", parentId = 1),
        OptionTag(tagId = 6, tagType = "OPTION2", name = "감성 인테리어", parentId = 1),
        OptionTag(tagId = 7, tagType = "OPTION2", name = "콘센트 많음", parentId = 1),
        OptionTag(tagId = 8, tagType = "OPTION2", name = "시간 제한 없음", parentId = 1),
        OptionTag(tagId = 9, tagType = "OPTION2", name = "채광 좋음", parentId = 1),
        OptionTag(tagId = 10, tagType = "OPTION2", name = "창가석 있음", parentId = 1),
    ),

    val selectedOptionFilter: PersistentList<Int> = persistentListOf()
) : UiState

sealed interface PlaceIntent : UiIntent {
    data object LoadPlaces : PlaceIntent
    data class PlaceClicked(val placeId: Int) : PlaceIntent
    data object Retry : PlaceIntent
    data class SelectOptionFilter(val optionTagId: Int) : PlaceIntent
}

sealed interface PlaceSideEffect : SideEffect {
    data class NavigateToMap(
        val placeId: Int
    ) : PlaceSideEffect
}