package com.teamsolply.solply.collection.collection.place

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.collection.repository.CollectionRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceCollectionViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository
) :
    BaseViewModel<PlaceCollectionState, PlaceCollectionIntent, PlaceCollectionSideEffect>(
        PlaceCollectionState()
    ) {
    override fun handleIntent(intent: PlaceCollectionIntent) {
        when (intent) {
            is PlaceCollectionIntent.Init -> {
                reduce {
                    copy(
                        townId = intent.townId,
                        townName = intent.townName
                    )
                }
                getPlaceList(townId = uiState.value.townId)
            }

            is PlaceCollectionIntent.SelectButtonClick -> {
                reduce {
                    copy(selectMode = true)
                }
            }

            is PlaceCollectionIntent.DeleteButtonClick -> {
                if (uiState.value.selectedPlaces.isNotEmpty()) {
                    reduce {
                        copy(dialogState = true)
                    }
                }
            }

            is PlaceCollectionIntent.CancelButtonClick -> {
                reduce {
                    val updatedPlaces = places.map {
                        if (it.isSelected) it.copy(isSelected = false) else it
                    }
                    copy(
                        places = updatedPlaces.toPersistentList(),
                        selectMode = false
                    )
                }
            }

            is PlaceCollectionIntent.BackButtonClick -> {
                postSideEffect(PlaceCollectionSideEffect.NavigateToBack)
            }

            is PlaceCollectionIntent.PlaceCardClick -> {
                if (uiState.value.selectMode) {
                    if (uiState.value.selectedPlaces.contains(intent.placeId)) {
                        reduce {
                            val updatedPlaces = places.map {
                                if (it.placeId == intent.placeId) it.copy(isSelected = false) else it
                            }.toPersistentList()
                            copy(
                                selectedPlaces = (selectedPlaces - intent.placeId).toPersistentSet(),
                                places = updatedPlaces
                            )
                        }
                    } else {
                        reduce {
                            val updatedPlaces = places.map {
                                if (it.placeId == intent.placeId) it.copy(isSelected = true) else it
                            }.toPersistentList()
                            copy(
                                selectedPlaces = (selectedPlaces + intent.placeId).toPersistentSet(),
                                places = updatedPlaces
                            )
                        }
                    }
                } else {
                    postSideEffect(PlaceCollectionSideEffect.NavigateToMap(intent.placeId))
                }
            }

            is PlaceCollectionIntent.DialogConfirmClick -> {
                deletePlaces(uiState.value.selectedPlaces.toList())
            }

            is PlaceCollectionIntent.DialogDismissClick -> {
                reduce {
                    copy(dialogState = false)
                }
            }
        }
    }

    private fun getPlaceList(townId: Long) {
        viewModelScope.launch {
            collectionRepository.getPlaceList(townId).onSuccess {
                reduce {
                    copy(
                        places = it.toPersistentList()
                    )
                }
            }
        }
    }

    private fun deletePlaces(selectedPlaces: List<Long>) {
        viewModelScope.launch {
            collectionRepository.deletePlaces(selectedPlaces).onSuccess {
                reduce {
                    copy(
                        selectedPlaces = emptySet(),
                        selectMode = false,
                        dialogState = false
                    )
                }
                getPlaceList(townId = uiState.value.townId)
            }
        }
    }
}
