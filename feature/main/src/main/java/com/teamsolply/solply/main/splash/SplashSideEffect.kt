package com.teamsolply.solply.main.splash

sealed interface SplashSideEffect {
    data object NavigateToOauth : SplashSideEffect
    data object NavigateToPlace : SplashSideEffect
}
