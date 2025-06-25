package com.teamsolply.solply.main.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun SplashScreen(
    navigateToOauth: () -> Unit,
    navigateToPlace: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Splash",
        )
        Text(
            text = "navigate to ouath",
            modifier = Modifier.customClickable(
                onClick = navigateToOauth
            )
        )
        Text(
            text = "navigate to place",
            modifier = Modifier.customClickable(
                onClick = navigateToPlace
            )
        )
    }
}