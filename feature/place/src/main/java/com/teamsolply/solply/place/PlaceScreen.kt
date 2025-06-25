package com.teamsolply.solply.place

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PlaceRoute(
    paddingValues: PaddingValues,
    viewModel: PlaceViewModel = hiltViewModel()
) {
    PlaceScreen()
}

@Composable
fun PlaceScreen(
    modifier: Modifier = Modifier

) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Place"
        )
    }
}
