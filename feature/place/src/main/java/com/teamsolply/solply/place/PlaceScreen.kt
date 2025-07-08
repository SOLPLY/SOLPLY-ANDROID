package com.teamsolply.solply.place

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.model.PlaceType
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
//        showNavigateSnackBar(" 네비게이트 테스트입니다.") { navigateToMaps(MapsType.PLACE_DETAIL.name) }
//        showTextSnackBar("텍스트")
//        showTextSnackBar("2번 텍스트")
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
        SolplyCourseCard(
            title = "asd",
            imgRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            placeType = listOf(PlaceType.FOOD, PlaceType.CAFE),
            backgroundColor = SolplyTheme.colors.red300,
            iconColor = SolplyTheme.colors.gray700,
            iconBackGroundColor = SolplyTheme.colors.green500
        )
        Image(
            painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy),
            contentDescription = "place_image",
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .padding(start = 8.dp, end = 9.dp, top = 8.dp, bottom = 8.dp)
        )
        Text(
            text = "Place",
            modifier = Modifier.customClickable { navigateToMaps(MapsType.PLACE_DETAIL.name) }
        )
    }
}
