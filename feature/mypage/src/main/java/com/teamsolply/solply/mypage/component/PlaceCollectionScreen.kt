package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.card.SolplyPlaceCard
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.MypageScreen
import com.teamsolply.solply.mypage.model.PlaceCard
import com.teamsolply.solply.ui.preview.DefaultPreview


@Composable
fun PlaceCollectionScreen(
    onClickEmptyButton: () -> Unit,
    place: List<PlaceCard>,
    modifier: Modifier = Modifier,
    isEmpty: Boolean = true
) {
    if (isEmpty) {
        EmptyCollectionScreen(
            onClick = onClickEmptyButton
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(place) {
                SolplyPlaceCard(
                    name = it.placeName,
                    placeType = it.placeType,
                    imgRes = it.imageUrls[0],
                )
            }
        }
    }
}

@DefaultPreview
@Composable
private fun MypageScreenPreview() {
    SolplyTheme {
        MypageScreen(
            navigateToMaps = {},
            place = emptyList()
        )
    }
}