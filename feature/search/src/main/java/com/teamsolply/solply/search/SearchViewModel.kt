package com.teamsolply.solply.search

import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : BaseViewModel<SearchState, SearchIntent, SearchSideEffect>(SearchState()) {
    override fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.ChangeSearchText -> reduce {
                copy(searchText = intent.searchText)
            }
        }
    }
}
