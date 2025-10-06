package com.teamsolply.solply.search

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.search.repository.SearchRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchState, SearchIntent, SearchSideEffect>(SearchState()) {

    private var searchJob: Job? = null

    override fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.ChangeSearchText -> {
                reduce {
                    copy(searchText = intent.searchText)
                }
                searchJob?.cancel()

                searchJob = viewModelScope.launch {
                    delay(500)
                    if (intent.searchText.isNotBlank()) {
                        performSearch(intent.searchText)
                    } else {
                        reduce { copy(searchResult = persistentListOf()) }
                    }
                }
            }

            SearchIntent.RegisterPlaceClick -> postSideEffect(SearchSideEffect.NavigateToRegisterPlace)
            is SearchIntent.PlaceDetailClick -> postSideEffect(
                SearchSideEffect.NavigateToPlaceDetail(
                    placeId = intent.placeId,
                    townId = intent.townId
                )
            )
        }
    }

    private suspend fun performSearch(keyword: String) {
        searchRepository.getPlaceSearch(keyword)
            .onSuccess { searchResults ->
                reduce { copy(searchResult = searchResults.toPersistentList()) }
            }
    }

    fun resetSearchState() {
        reduce {
            copy(
                searchText = "",
                searchResult = persistentListOf()
            )
        }
    }
}
