package com.teamsolply.solply.collection.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.collection.model.PlaceTownEntity
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.image.AdaptationImage

@Composable
fun PlaceTownCollectionScreen(
    town: List<PlaceTownEntity>,
    onClickTown: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(top = 16.dp, start = 17.dp, end = 17.dp)
    ) {
        itemsIndexed(town) { index, it ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .customClickable(
                        rippleEnabled = false
                    ) {
                        onClickTown(it.townId, it.townName)
                    },
                contentAlignment = if (index % 2 == 0) {
                    Alignment.CenterEnd
                } else {
                    Alignment.CenterStart
                }
            ) {
                SolplyTownCard(
                    town = it.townName,
                    modifier =
                    if (index % 2 == 0) {
                        Modifier.padding(end = 5.dp)
                    } else {
                        Modifier.padding(start = 5.dp)
                    },
                    content = {
                        AdaptationImage(
                            imageUrl = it.imageUrl,
                            modifier = Modifier
                                .matchParentSize()
                                .clip(
                                    RoundedCornerShape(20.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )
                    }
                )
            }
        }
    }
}
