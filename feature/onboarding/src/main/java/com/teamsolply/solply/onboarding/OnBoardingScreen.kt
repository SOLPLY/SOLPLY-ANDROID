package com.teamsolply.solply.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OnBoardingRoute(
    paddingValues: PaddingValues,
    navigateToPlace: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                OnBoardingSideEffect.NavigateToPlace -> navigateToPlace()
            }
        }
    }

    OnBoardingScreen(
        onBoardingButtonClick = { viewModel.sendIntent(OnBoardingIntent.OnBoardingButtonClick) }
    )
}

@Composable
fun OnBoardingScreen(
    onBoardingButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "onboarding",
            modifier = Modifier.customClickable(
                onClick = onBoardingButtonClick
            )
        )
    }
}