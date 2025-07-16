package com.teamsolply.solply.place

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.place.model.PlaceData
import com.teamsolply.solply.place.model.RecommendPlaceInfo
import com.teamsolply.solply.place.model.SelectedTownInfo
import com.teamsolply.solply.place.model.TagEntity
import com.teamsolply.solply.place.model.UserInfo
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import okhttp3.internal.immutableListOf


data class PlaceState(
    val townId: Long = 0,

    val userInfo: UserInfo = UserInfo(
        userId = 1,
        nickname = "숭이숭이숭이",
        selectedTown = SelectedTownInfo(0, "망원동"),
        persona = "REST"
    ),

    val recommendPlaces: List<RecommendPlaceInfo> = immutableListOf(
        RecommendPlaceInfo(
            placeId = 0,
            placeName = "장소 이름",
            thumbnailImageUrl = "",
            primaryTag = PlaceType.CAFE,
            introduction = "장소 한 줄 소개 장소 한 줄 소개"
        ),
        RecommendPlaceInfo(
            placeId = 1,
            placeName = "장소 이름",
            thumbnailImageUrl = "",
            primaryTag = PlaceType.FOOD,
            introduction = "장소 한 줄 소개 장소 한 줄 소개"
        ),
        RecommendPlaceInfo(
            placeId = 2,
            placeName = "장소 이름",
            thumbnailImageUrl = "",
            primaryTag = PlaceType.UNIQUE_SPACE,
            introduction = "장소 한 줄 소개 장소 한 줄 소개 두 줄이 되어도 괜찮음음음음음음"
        )
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
            primaryTag = PlaceType.UNIQUE_SPACE,
            isBookmarked = true
        ),
        PlaceData(
            placeId = 4,
            placeName = "하현상최고",
            thumbnailUrl = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            primaryTag = PlaceType.WALKING,
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
            primaryTag = PlaceType.BOOKSTORE,
            isBookmarked = false
        )
    ),
    val selectedMainTagId: Int = 0,

    val mainFilterItems: List<TagEntity> = listOf(

    ),
    val subFilterItems: List<TagEntity>? = listOf(

    ),

    val isMainFilterBottomSheetVisible: Boolean = false,
    val selectedMainFilter: String = "ALL",

    val isOptionFilterBottomSheetVisible: Boolean = false,
    val selectedOptionFilter: PersistentList<Int> = persistentListOf()

) : UiState

sealed interface PlaceIntent : UiIntent {
    data object LoadUserInfo : PlaceIntent
    data class LoadPlaces(val townId: Long) : PlaceIntent
    data object LoadMainTags : PlaceIntent
    data class LoadSubTags(val parentId: Int) : PlaceIntent
    data class PlaceClicked(val placeId: Long) : PlaceIntent
    data object Retry : PlaceIntent
    data class SelectOptionFilter(val optionTagId: Int) : PlaceIntent
    data object ClearOptionFilter : PlaceIntent

    data object ChangeMainFilterBottomSheetVisible : PlaceIntent
    data class ChangeSelectedMainFilter(
        val mainFilterId: Int,
        val mainFilterName: String
    ) : PlaceIntent

    data object ChangeOptionFilterBottomSheetVisible : PlaceIntent
    data class ChangeSelectedOptionFilter(
        val optionFilterId: Int
    ) : PlaceIntent
}

sealed interface PlaceSideEffect : SideEffect {
    data class NavigateToMap(
        val placeId: Long
    ) : PlaceSideEffect
}
