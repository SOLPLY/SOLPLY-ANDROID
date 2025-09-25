package com.teamsolply.solply.search

import com.teamsolply.solply.search.model.SearchResultEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val searchText: String = "",
    val searchResult: PersistentList<SearchResultEntity> = persistentListOf()
) : UiState {
    val resultCount: Int get() = searchResult.size
    val hasQuery: Boolean get() = searchText.isNotBlank()
}

sealed interface SearchIntent : UiIntent {
    data class ChangeSearchText(
        val searchText: String
    ) : SearchIntent
}

sealed interface SearchSideEffect : SideEffect
