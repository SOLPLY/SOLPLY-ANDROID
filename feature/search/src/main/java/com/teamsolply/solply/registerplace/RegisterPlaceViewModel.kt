package com.teamsolply.solply.registerplace

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.model.PlaceType.Companion.toId
import com.teamsolply.solply.search.model.RegisterPlaceEntity
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

            is RegisterPlaceIntent.ChangePlaceTypeDropDownVisible -> {
                reduce {
                    copy(
                        isPlaceTypeDropdownExpanded = !isPlaceTypeDropdownExpanded,
                    )
                }
            }

            is RegisterPlaceIntent.ClickDropDownItem -> {
                reduce {
                    copy(
                        selectedPlaceType = intent.placeType,
                        isPlaceTypeDropdownExpanded = !isPlaceTypeDropdownExpanded
                    )
                }
            }

            is RegisterPlaceIntent.ClickPlaceKeyword -> {
                reduce {
                    val updatedKeywords =
                        if (selectedPlaceKeyword.contains(intent.placeKeywordId)) {
                            selectedPlaceKeyword - intent.placeKeywordId
                        } else {
                            selectedPlaceKeyword + intent.placeKeywordId
                        }
                    copy(selectedPlaceKeyword = updatedKeywords)
                }
            }

            is RegisterPlaceIntent.ClickPlaceFeature -> {
                reduce {
                    val updatedFeatures =
                        if (selectedPlaceFeature.contains(intent.placeFeatureId)) {
                            selectedPlaceFeature - intent.placeFeatureId
                        } else {
                            selectedPlaceFeature + intent.placeFeatureId
                        }
                    copy(selectedPlaceFeature = updatedFeatures)
                }
            }

            is RegisterPlaceIntent.InputRegisterPlaceReason -> reduce {
                copy(registerPlaceReason = intent.text)
            }

            is RegisterPlaceIntent.SelectedReportUris -> reduce {
                val current = selectedReportUris
                val remain = (3 - current.size).coerceAtLeast(0)
                val income = intent.uris.take(remain)
                copy(selectedReportUris = (current + income))
            }

            is RegisterPlaceIntent.ClickRegisterPlace -> registerPlace()
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

    private fun registerPlace() {
        viewModelScope.launch {
            searchRepository.requestsPlaces(
                registerPlaceEntity = RegisterPlaceEntity(
                    placeName = uiState.value.placeName,
                    address = uiState.value.placeAddress,
                    mainTagId = uiState.value.selectedPlaceType.toId(),
                    subTagAIds = uiState.value.selectedPlaceKeyword,
                    subTagBIds = uiState.value.selectedPlaceFeature,
                    reason = uiState.value.registerPlaceReason,
                    images = listOf()
                )
            )
        }
    }
}
