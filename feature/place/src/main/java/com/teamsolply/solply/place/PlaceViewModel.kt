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
                    reduce { copy(places = placesList) }
                }
            // failures ignored; Retry intent can re-trigger
        }
    }
}
