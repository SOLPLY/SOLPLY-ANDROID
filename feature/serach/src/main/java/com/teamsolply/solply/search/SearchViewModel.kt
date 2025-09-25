package com.teamsolply.solply.search

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.place.repository.PlaceRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: PlaceRepository
) : BaseViewModel<SearchState, SearchIntent, SearchSideEffect>(SearchState()) {

    private var typingJob: Job? = null

    init {
        reduce { copy(query = "", results = emptyList<SearchItemUi>().toPersistentList()) }
    }

    override fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.QueryChanged -> {
                reduce { copy(query = intent.value) }
                typingJob?.cancel()
                typingJob = viewModelScope.launch {
                    delay(250)
                    search(currentState.query)
                }
            }
            SearchIntent.ClearQuery -> {
                typingJob?.cancel()
                reduce {
                    copy(
                        query = "",
                        isLoading = false,
                        results = emptyList<SearchItemUi>().toPersistentList(),
                        selectedTownId = null
                    )
                }
            }
            SearchIntent.Submit -> {
                viewModelScope.launch { search(currentState.query) }
            }
            is SearchIntent.ClickItem -> {
                val townId = currentState.selectedTownId ?: return
                postSideEffect(
                    SearchSideEffect.NavigateToPlaceDetail(
                        townId = townId,
                        placeId = intent.id
                    )
                )
            }
            SearchIntent.ClickNoResult -> {
                postSideEffect(SearchSideEffect.NavigateToNoResult)
            }
            SearchIntent.Retry -> {
                // 필요 시 최근 검색어 복구/추천 키워드 로딩 등을 붙일 자리
            }
        }
    }

    private suspend fun search(query: String) {
        if (query.isBlank()) {
            reduce { copy(isLoading = false, results = emptyList<SearchItemUi>().toPersistentList()) }
            return
        }
        reduce { copy(isLoading = true) }

        val filtered = DUMMY_RESULTS.filter { item ->
            val normalizedQuery = query.trim().lowercase()
            item.name.lowercase().contains(normalizedQuery) || item.address.lowercase().contains(normalizedQuery)
        }

        val townId = repository.getUserInfo()
            .map { it.selectedTown.townId }
            .getOrDefault(DEFAULT_TOWN_ID)

        reduce {
            copy(
                isLoading = false,
                selectedTownId = townId,
                results = filtered.toPersistentList()
            )
        }
    }

    private companion object {
        const val DEFAULT_TOWN_ID = 0L

        val DUMMY_RESULTS = listOf(
            SearchItemUi(
                id = 1,
                name = "솔플리솔플리",
                tag = PlaceType.FOOD,
                address = "주소주소주소주소",
                imageUrl = ""
            ),
            SearchItemUi(
                id = 2,
                name = "솔플리솔플리",
                tag = PlaceType.BOOKSTORE,
                imageUrl = ""
            ),
            SearchItemUi(
                id = 3,
                name = "솔플리솔플리",
                tag = PlaceType.UNIQUE_SPACE,
                address = "주소주소주소주소주소주소주소주주소주소주소주소",
                imageUrl = ""
            )
        )
    }
}
