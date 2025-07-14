package com.teamsolply.solply.place

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.place.repository.PlaceRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: PlaceRepository
) : BaseViewModel<PlaceState, PlaceIntent, PlaceSideEffect>(PlaceState()) {

    init {
        sendIntent(PlaceIntent.LoadPlaces)
    }

    override fun handleIntent(intent: PlaceIntent) {
        when (intent) {
            PlaceIntent.LoadPlaces -> fetchPlaces()
            is PlaceIntent.PlaceClicked -> postSideEffect(PlaceSideEffect.NavigateToMap(intent.placeId))
            PlaceIntent.Retry -> fetchPlaces()
            is PlaceIntent.SelectOptionFilter -> {
                val currentOptionFilter = intent.optionTagId

                // TODO. currentOptionFilter가 selectedOptionFilter에 없으면 추가 있으면 삭제
                reduce {
                    copy(
                        selectedOptionFilter = selectedOptionFilter
                    )
                }
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
