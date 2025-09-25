package com.teamsolply.solply.search

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val results: PersistentList<SearchItemUi> = persistentListOf(),
    val selectedTownId: Long? = null
) : UiState {
    val resultCount: Int get() = results.size
    val hasQuery: Boolean get() = query.isNotBlank()
}

data class SearchItemUi(
    val id: Long,
    val name: String,
    val tag: PlaceType,
    val address: String,
    val imageUrl: String
)

sealed interface SearchIntent : UiIntent {
    data class QueryChanged(val value: String) : SearchIntent
    data object ClearQuery : SearchIntent
    data object Submit : SearchIntent
    data class ClickItem(val id: Long) : SearchIntent
    data object ClickNoResult : SearchIntent
    data object Retry : SearchIntent
}

sealed interface SearchSideEffect : SideEffect {
    data class NavigateToPlaceDetail(val townId: Long, val placeId: Long) : SearchSideEffect
    data object NavigateToNoResult : SearchSideEffect
}
