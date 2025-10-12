package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.card.SolplyPlaceCard
import com.teamsolply.solply.designsystem.component.topbar.SolplyTopBar
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.R
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun MypagePlaceAllScreen(
    placeList: List<PlaceInfoEntity>,
    onBackButtonClick: () -> Unit,
    onPlaceCardClick: (Long, Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = SolplyTheme.colors.white),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SolplyTopBar(
            barText = stringResource(R.string.mypage_place),
            onBackButtonClick = { onBackButtonClick() }
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
            content = {
                itemsIndexed(placeList) { index, it ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .customClickable(
                                rippleEnabled = false
                            ) {
                                onPlaceCardClick(it.townId, it.placeId)
                            },
                        contentAlignment = if (index % 2 == 0) {
                            Alignment.CenterEnd
                        } else {
                            Alignment.CenterStart
                        }
                    ) {
                        SolplyPlaceCard(
                            name = it.placeName,
                            placeType = it.placeType,
                            imgRes = it.imageUrls,
                            selected = it.isSelected,
                            saved = it.isSaved,
                            touchable = false,
                            modifier =
                            if (index % 2 == 0) {
                                Modifier.padding(end = 5.dp)
                            } else {
                                Modifier.padding(start = 5.dp)
                            }
                        )
                    }
                }
                item(span = { GridItemSpan(2) }) {
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        )
    }
}
