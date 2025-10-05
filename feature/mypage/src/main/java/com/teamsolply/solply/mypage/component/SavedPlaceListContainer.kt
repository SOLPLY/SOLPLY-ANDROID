package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.card.SolplyPlaceCard
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.model.PlaceInfoEntity

@Composable
fun SavedPlaceListContainer(
    savedPlaceList: List<PlaceInfoEntity>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(savedPlaceList) {
            SolplyPlaceCard(
                name = it.placeName,
                placeType = it.placeType,
                imgRes = it.imageUrls,
                selected = it.isSelected,
                saved = it.isSaved,
                touchable = false,
                modifier = Modifier.size(height = 165.dp, width = 136.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SavedPlaceListContainerPreview() {
    SolplyTheme {
        SavedPlaceListContainer(
            savedPlaceList = emptyList()
        )
    }
}