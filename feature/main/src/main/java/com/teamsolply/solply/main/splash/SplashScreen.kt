package com.teamsolply.solply.main.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.main.R
import androidx.compose.foundation.layout.size

@Composable
fun SplashScreen(
    navigateToOauth: () -> Unit,
    navigateToPlace: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                SplashSideEffect.NavigateToOauth -> navigateToOauth()
                SplashSideEffect.NavigateToPlace -> navigateToPlace()
            }
        }
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
    val progress by animateLottieCompositionAsState(composition)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SolplyTheme.colors.green300),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(122.dp)
        )
    }
}

