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
    val userInfo: UserInfo = UserInfo(
        userId = 1,
        nickname = "숭이숭이숭이",
        selectedTown = SelectedTownInfo(0, "망원동"),
        persona = "REST"
    ),

    val recommendPlaces: PersistentList<RecommendPlaceInfo> = persistentListOf(),

    val placeList: PersistentList<PlaceData> = persistentListOf(),

    val selectedMainFilterId: Int = 0,
    val selectedMainFilter: String = "ALL",

    val mainFilterItems: PersistentList<TagEntity> = persistentListOf(),
    val subFilterItems: PersistentList<TagEntity>? = persistentListOf(),

    val isMainFilterBottomSheetVisible: Boolean = false,

    val isOptionFilterBottomSheetVisible: Boolean = false,
    val selectedOptionAFilter: PersistentList<Int> = persistentListOf(),
    val selectedOptionBFilter: PersistentList<Int> = persistentListOf()

) : UiState

sealed interface PlaceIntent : UiIntent {
    data class PlaceClicked(val placeId: Long) : PlaceIntent
    data object Retry : PlaceIntent

    data object MainFilterChipClick : PlaceIntent
    data object MainFilterBottomSheetDismiss : PlaceIntent
    data class MainFilterClick(
        val mainFilterId: Int,
        val mainFilterName: String
    ) : PlaceIntent

    data object SubFilterChipClick : PlaceIntent
    data object SubFilterBottomSheetCompleteButtonClick : PlaceIntent
    data object SubFilterBottomSheetResetButtonClick : PlaceIntent
    data object SubFilterBottomSheetDismiss : PlaceIntent
    data class SubFilterClick(
        val optionFilterId: Int,
        val selectedTagType: String
    ) : PlaceIntent
}

sealed interface PlaceSideEffect : SideEffect {
    data class NavigateToMap(
        val placeId: Long
    ) : PlaceSideEffect
}
