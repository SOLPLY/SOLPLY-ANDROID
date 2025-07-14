package com.teamsolply.solply.place

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.place.model.SaveAutoSignInEntity
import com.teamsolply.solply.place.repository.PlaceRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: PlaceRepository
) : BaseViewModel<PlaceState, PlaceIntent, PlaceSideEffect>(PlaceState()) {

    init {
        viewModelScope.launch {
            repository.saveAutoSignIn(autoSignIn = SaveAutoSignInEntity(autoSignIn = true))
            sendIntent(PlaceIntent.LoadPlaces)
        }
    }

    override fun handleIntent(intent: PlaceIntent) {
        when (intent) {
            PlaceIntent.LoadPlaces -> fetchPlaces()
            is PlaceIntent.PlaceClicked -> postSideEffect(PlaceSideEffect.NavigateToMap(intent.placeId))
            PlaceIntent.Retry -> fetchPlaces()

            is PlaceIntent.SelectOptionFilter -> {
                val currentOptionFilter = intent.optionTagId
                val updatedOptionFilter =
                    if (uiState.value.selectedOptionFilter.contains(currentOptionFilter)) {
                        uiState.value.selectedOptionFilter - currentOptionFilter
                    } else {
                        uiState.value.selectedOptionFilter + currentOptionFilter
                    }
                reduce {
                    copy(
                        selectedOptionFilter = updatedOptionFilter.toPersistentList()
                    )
                }
            }

            // 메인 필터 바텀시트 visible
            PlaceIntent.ChangeMainFilterBottomSheetVisible -> reduce {
                copy(isMainFilterBottomSheetVisible = !isMainFilterBottomSheetVisible)
            }

            // 메인 필터 변경
            is PlaceIntent.ChangeSelectedMainFilter -> reduce {
                copy(selectedMainFilter = intent.mainFilterName)
            }

            // 옵션 필터 바텀시트 visible
            PlaceIntent.ChangeOptionFilterBottomSheetVisible -> reduce {
                copy(isOptionFilterBottomSheetVisible = !isOptionFilterBottomSheetVisible)
            }

            // 옵션 필터 변경
            is PlaceIntent.ChangeSelectedOptionFilter -> reduce {
                val updatedList = selectedOptionFilter.toMutableList().apply {
                    if (contains(intent.optionFilterId)) {
                        remove(intent.optionFilterId)
                    } else {
                        add(
                            intent.optionFilterId
                        )
                    }
                }

                copy(
                    selectedOptionFilter = updatedList.toPersistentList()
                )
            }

            // 옵션 필터 초기화
            PlaceIntent.ClearOptionFilter -> reduce {
                copy(selectedOptionFilter = persistentListOf())
            }
        }
    }

    private fun fetchPlaces() {
        viewModelScope.launch {
            repository.getRecommendedPlace()
                .onSuccess { placesList ->
                    reduce { copy(recommendPlaces = placesList) }
                }
        }
    }

    fun onMainTypeSelected(type: String) {
        val tags = when (type) {
            "WALK", "BOOK" -> emptyList()
            else -> listOf(
                OptionTag(9, "OPTION1", "커피/디저트", 1),
                OptionTag(10, "OPTION2", "시그니쳐메뉴", 1)
            )
        }
        reduce { copy(optionTags = tags) }
    }
}
