package com.teamsolply.solply.place

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.place.repository.PlaceRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: PlaceRepository
) : BaseViewModel<PlaceState, PlaceIntent, PlaceSideEffect>(PlaceState()) {

    init {
        sendIntent(PlaceIntent.LoadPlaces)
    }

    override fun handleIntent(intent: PlaceIntent) {
        when (intent) {
            PlaceIntent.LoadPlaces      -> fetchPlaces()
            is PlaceIntent.PlaceClicked -> postSideEffect(PlaceSideEffect.NavigateToMap)
            PlaceIntent.Retry           -> fetchPlaces()
        }
    }

    private fun fetchPlaces() {
        viewModelScope.launch {
            repository.getRecommendedPlace()
                .onSuccess { placesList ->
                    reduce { copy(recommendplaces = placesList) }
                }
            // failures ignored; Retry intent can re-trigger
        }
    }

    fun onMainTypeSelected(type: String) {
        val tags = when(type) {
            "WALK", "BOOK" -> emptyList()
            else -> listOf(
                OptionTag(9, "OPTION1", "커피/디저트", 1),
                OptionTag(10, "OPTION2", "시그니쳐메뉴", 1)
            )
        }
        reduce { copy(optionTags = tags) }
    }
}
