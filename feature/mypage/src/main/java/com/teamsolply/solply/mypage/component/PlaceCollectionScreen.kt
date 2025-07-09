package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.card.SolplyPlaceCard
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.PlaceCard
import com.teamsolply.solply.ui.preview.DefaultPreview
import okhttp3.internal.immutableListOf

@Composable
fun PlaceCollectionScreen(
    onClickEmptyButton: () -> Unit,
    place: List<PlaceCard>,
    modifier: Modifier = Modifier
) {
    if (place.isEmpty()) {
        EmptyCollectionScreen(
            onClick = onClickEmptyButton,
            mypageTab = MypageTab.PLACE
        )
    } else {
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(Dp(10f)),
            contentPadding = PaddingValues(top = 16.dp)
        ) {
            items(place) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    SolplyPlaceCard(
                        name = it.placeName,
                        placeType = it.placeType,
                        imgRes = it.imageUrls[0]
                    )
                }
            }
        }
    }
}

@DefaultPreview
@Composable
private fun PlaceCollectionScreenPreview() {
    SolplyTheme {
        PlaceCollectionScreen(
            onClickEmptyButton = {},
            place = immutableListOf(
                PlaceCard(
                    placeId = 0,
                    placeName = "0번",
                    placeType = PlaceType.CAFE,
                    imageUrls = listOf(R.drawable.img_course_dummy)
                ),
                PlaceCard(
                    placeId = 1,
                    placeName = "1번",
                    placeType = PlaceType.BOOK,
                    imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
                ),
                PlaceCard(
                    placeId = 2,
                    placeName = "2번",
                    placeType = PlaceType.SHOPPING,
                    imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
                ),
                PlaceCard(
                    placeId = 3,
                    placeName = "3번",
                    placeType = PlaceType.FOOD,
                    imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
                )
            )
        )
    }
}
