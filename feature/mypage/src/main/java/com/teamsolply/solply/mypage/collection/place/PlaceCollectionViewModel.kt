package com.teamsolply.solply.mypage.collection.place

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.mypage.repository.MypageRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceCollectionViewModel @Inject constructor(
    private val mypageRepository: MypageRepository
) :
    BaseViewModel<PlaceCollectionState, PlaceCollectionIntent, PlaceCollectionSideEffect>(
        PlaceCollectionState()
    ) {
    override fun handleIntent(intent: PlaceCollectionIntent) {
        when (intent) {
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
                        places = updatedPlaces,
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
                            val updatedPlaces = places.toMutableList()
                            val oldPlace = updatedPlaces[intent.index]
                            updatedPlaces[intent.index] = oldPlace.copy(isSelected = false)
                            copy(
                                selectedPlaces = selectedPlaces - intent.placeId,
                                places = updatedPlaces
                            )
                        }
                    } else {
                        reduce {
                            val updatedPlaces = places.toMutableList()
                            val oldPlace = updatedPlaces[intent.index]
                            updatedPlaces[intent.index] = oldPlace.copy(isSelected = true)
                            copy(
                                selectedPlaces = selectedPlaces + intent.placeId,
                                places = updatedPlaces
                            )
                        }
                    }
                } else {
                    postSideEffect(PlaceCollectionSideEffect.NavigateToMap)
                }
            }

            is PlaceCollectionIntent.DialogConfirmClick -> {
                reduce {
                    copy(dialogState = false)
                }
                // TODO 삭제 api 요청
                deletePlaces(uiState.value.selectedPlaces.toList())
                // TODO 삭제 api 응답 후
                reduce {
                    copy(selectMode = false)
                }
                getPlaceList()
                postSideEffect(PlaceCollectionSideEffect.DeletePlaces)
            }

            is PlaceCollectionIntent.DialogDismissClick -> {
                reduce {
                    copy(dialogState = false)
                }
            }
        }
    }

    fun getPlaceList() {
        viewModelScope.launch {
            mypageRepository.getPlaceList().onSuccess {
                reduce {
                    copy(
                        places = it
                    )
                }
            }
        }
    }

    fun deletePlaces(selectedPlaces: List<Int>) {
        viewModelScope.launch {
            mypageRepository.deleteCourses(selectedPlaces).onSuccess {
                reduce {
                    copy(

                    )
                }
            }
        }
    }
}
