package com.teamsolply.solply.registerplace

import android.net.Uri
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
    // 장소 키워드
    val selectedPlaceKeyword: List<Long> = persistentListOf(),
    // 장소 특징
    val selectedPlaceFeature: List<Long> = persistentListOf(),
    // 장소 이유
    val registerPlaceReason: String = "",
    // 장소 사진
    val selectedReportUris: List<Uri> = persistentListOf(),
    // 로티
    val registerLottieVisibility: Boolean = false,
    val isRegisterSuccess: Boolean = false
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

    data object ChangePlaceTypeDropDownVisible : RegisterPlaceIntent
    data class ClickDropDownItem(val placeType: PlaceType) : RegisterPlaceIntent
    data class ClickPlaceKeyword(val placeKeywordId: Long) : RegisterPlaceIntent
    data class ClickPlaceFeature(val placeFeatureId: Long) : RegisterPlaceIntent
    data class InputRegisterPlaceReason(val text: String) : RegisterPlaceIntent
    data class SelectedReportUris(val uris: List<Uri>) : RegisterPlaceIntent
    data object ClickRegisterPlace : RegisterPlaceIntent
    data class ChangeRegisterLottieVisibility(val visible: Boolean) : RegisterPlaceIntent
}

sealed interface RegisterPlaceSideEffect : SideEffect {
    data object NavigateToBack : RegisterPlaceSideEffect
}