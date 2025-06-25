package com.teamsolply.solply.place

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.place.model.SaveAutoSignInEntity
import com.teamsolply.solply.place.repository.PlaceRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) :
    BaseViewModel<PlaceState, PlaceIntent, PlaceSideEffect>(
        PlaceState()
    ) {

    init {
        viewModelScope.launch {
            placeRepository.saveAutoSignIn(autoSignIn = SaveAutoSignInEntity(autoSignIn = true))
        }
    }

    override fun handleIntent(intent: PlaceIntent) {
        TODO("Not yet implemented")
    }
}