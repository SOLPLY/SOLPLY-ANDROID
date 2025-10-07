package com.teamsolply.solply.registerplace

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.search.model.NaverLocalSearchResponseEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import kotlinx.collections.immutable.persistentListOf

data class RegisterPlaceState(
    val placeName: String = "",
    val placeAddress: String = "",
    val selectedPlaceType: PlaceType? = null,
    val searchResults: List<NaverLocalSearchResponseEntity> = persistentListOf(),

    // 첫번쨰
    val isPlaceNameSuccess: Boolean = false,

    // 두번째
    val isPlaceTypeDropdownExpanded: Boolean = false,
) : UiState {
    val resultCount: Int get() = searchResults.size
    val hasQuery: Boolean get() = placeName.isNotBlank()
}

sealed interface RegisterPlaceIntent : UiIntent {
    data class InputPlaceNameText(
        val text: String
    ) : RegisterPlaceIntent

    data class SelectPlaceName(
        val placeName: String,
        val placeAddress: String
    ) : RegisterPlaceIntent

    data class SelectPlaceType(
        val placeType: PlaceType?
    ) : RegisterPlaceIntent
}

sealed interface RegisterPlaceSideEffect : SideEffect