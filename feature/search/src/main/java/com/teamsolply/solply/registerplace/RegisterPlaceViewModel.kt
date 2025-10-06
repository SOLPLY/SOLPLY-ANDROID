package com.teamsolply.solply.registerplace

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.search.repository.SearchRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterPlaceViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel<RegisterPlaceState, RegisterPlaceIntent, RegisterPlaceSideEffect>(
    RegisterPlaceState()
) {
    override fun handleIntent(intent: RegisterPlaceIntent) {
        when (intent) {
            is RegisterPlaceIntent.InputPlaceNameText -> {
                searchAddress(intent.text)
                reduce {
                    copy(placeName = intent.text)
                }
            }
        }
    }

    private fun searchAddress(query: String) {
        viewModelScope.launch {
            searchRepository.searchAddress(query = query)
                .onSuccess {
                    Log.d("asdasdasd", it.toString())
                }.onFailure {
                    Log.d("asdasdasd", it.toString())
                }
        }
    }
}
