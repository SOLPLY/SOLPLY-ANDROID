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
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.place.util.LocationPermissionRequest
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun PlaceRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    viewModel: PlaceViewModel = hiltViewModel()
) {
    LocationPermissionRequest()
    PlaceScreen(
        navigateToMaps = navigateToMaps
    )
}

@Composable
fun PlaceScreen(
    navigateToMaps: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Place",
            modifier = Modifier.customClickable { navigateToMaps(MapsType.PLACE_DETAIL.name) }
        )
    }
}