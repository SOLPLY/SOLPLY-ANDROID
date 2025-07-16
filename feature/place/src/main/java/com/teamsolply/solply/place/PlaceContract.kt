package com.teamsolply.solply.place

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

data class PlaceState(
    val townId: Long = 0,

    val userInfo: UserInfo = UserInfo(
        userId = 1,
        nickname = "숭이숭이숭이",
        selectedTown = SelectedTownInfo(0, "망원동"),
        persona = "REST"
    ),

    val recommendPlaces: PersistentList<RecommendPlaceInfo> = persistentListOf(),

    val placeList: PersistentList<PlaceData> = persistentListOf(),

    val selectedMainTagId: Int = 0,

    val mainFilterItems: PersistentList<TagEntity> = persistentListOf(),
    val subFilterItems: PersistentList<TagEntity>? = persistentListOf(),

    val isMainFilterBottomSheetVisible: Boolean = false,
    val selectedMainFilter: String = "ALL",

    val isOptionFilterBottomSheetVisible: Boolean = false,
    val selectedOptionFilter: PersistentList<Int> = persistentListOf()

) : UiState

sealed interface PlaceIntent : UiIntent {
    data object LoadUserInfo : PlaceIntent
    data class LoadPlaces(val townId: Long) : PlaceIntent
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
