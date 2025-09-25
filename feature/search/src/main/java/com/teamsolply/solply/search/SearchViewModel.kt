package com.teamsolply.solply.search

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.search.model.SearchResultEntity
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
                    }
                }
            }
        }
    }

    private suspend fun performSearch(keyword: String) {



        reduce {
            // copy(searchResult = searchResults.toPersistentList())
            copy(
                searchResult = persistentListOf(
                    SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),
                    SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),
                    SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),
                    SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),  SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),  SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),  SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),
                    SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),  SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),  SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),  SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),
                    SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),  SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),  SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),  SearchResultEntity(
                        placeId = 1,
                        thumbnailImageUrl = "asd",
                        primaryTag = PlaceType.CAFE,
                        address = "asd",
                        isBookmarked = true,
                        placeName = "As"
                    ),
                )
            )
        }




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
