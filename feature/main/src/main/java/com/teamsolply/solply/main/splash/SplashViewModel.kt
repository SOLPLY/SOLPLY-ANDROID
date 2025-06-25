package com.teamsolply.solply.main.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.main.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect: SharedFlow<SplashSideEffect> get() = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            mainRepository.getAutoSignIn().onSuccess { autoSignIn ->
                _sideEffect.emit(
                    if (autoSignIn) {
                        SplashSideEffect.NavigateToPlace
                    } else {
                        SplashSideEffect.NavigateToOauth
                    }
                )
            }
        }
    }
}
