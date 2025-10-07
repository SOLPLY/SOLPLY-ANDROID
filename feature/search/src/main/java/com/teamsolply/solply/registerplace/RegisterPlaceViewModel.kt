package com.teamsolply.solply.registerplace

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.search.repository.SearchRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterPlaceViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel<RegisterPlaceState, RegisterPlaceIntent, RegisterPlaceSideEffect>(
    RegisterPlaceState()
) {
    private var searchJob: Job? = null

    override fun handleIntent(intent: RegisterPlaceIntent) {
        when (intent) {
            is RegisterPlaceIntent.InputPlaceNameText -> {
                reduce {
                    copy(placeName = intent.text)
                }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500)
                    if (intent.text.length >= 2) {
                        searchAddress(intent.text)
                    }
                }
            }

            is RegisterPlaceIntent.SelectPlaceName -> {
                reduce {
                    copy(
                        isPlaceNameSuccess = !isPlaceNameSuccess,
                        placeName = intent.placeName,
                        placeAddress = intent.placeAddress
                    )
                }
            }

            is RegisterPlaceIntent.SelectPlaceType -> {
                reduce {
                    copy(
                        isPlaceTypeDropdownExpanded = !isPlaceTypeDropdownExpanded,
                        selectedPlaceType = intent.placeType
                    )
                }
            }
        }
    }

    private fun searchAddress(query: String) {
        viewModelScope.launch {
            searchRepository.searchAddress(query = query)
                .onSuccess {
                    reduce {
                        copy(searchResults = it)
                    }
                }
        }
    }
}
