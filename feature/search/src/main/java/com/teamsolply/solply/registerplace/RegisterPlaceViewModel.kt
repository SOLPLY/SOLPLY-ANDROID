package com.teamsolply.solply.registerplace

import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterPlaceViewModel @Inject constructor(
) : BaseViewModel<RegisterPlaceState, RegisterPlaceIntent, RegisterPlaceSideEffect>(RegisterPlaceState()) {
    override fun handleIntent(intent: RegisterPlaceIntent) {
        TODO("Not yet implemented")
    }
}
