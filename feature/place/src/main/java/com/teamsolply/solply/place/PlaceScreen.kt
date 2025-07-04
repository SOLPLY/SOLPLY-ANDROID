package com.teamsolply.solply.place

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.place.util.LocationPermissionRequest
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun PlaceRoute(
    paddingValues: PaddingValues,
    showNavigateSnackBar: (String, () -> Unit) -> Unit,
    showTextSnackBar: (String) -> Unit,
    navigateToMaps: (String) -> Unit,
    viewModel: PlaceViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        showNavigateSnackBar(" 네비게이트 테스트입니다.") { navigateToMaps(MapsType.PLACE_DETAIL.name) }
        showTextSnackBar("텍스트")
        showTextSnackBar("2번 텍스트")
    }
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
