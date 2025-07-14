package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.TownCard
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.preview.DefaultPreview

@Composable
fun TownCollectionScreen(
    town: List<TownCard>,
    onClickTown: (String) -> Unit,
    mypageTab: MypageTab,
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
                        onClickTown(it.townName)
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
                        when (mypageTab) {
                            MypageTab.PLACE ->
                                Image(
                                    painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy),
                                    contentDescription = ""
                                )

                            MypageTab.COURSE ->
                                SolplyCourseCard(
                                    title = "오감으로 수집하는 하루",
                                    imgRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                                    placeType = listOf(PlaceType.BOOK, PlaceType.SHOPPING),
                                    backgroundColor = SolplyTheme.colors.purple200,
                                    iconColor = SolplyTheme.colors.purple400,
                                    iconBackGroundColor = SolplyTheme.colors.purple100,
                                    savedPlace = true,
                                    savedCourse = true,
                                    onClick = { onClickTown(it.townName) }
                                )
                        }
                    }
                )
            }
        }
    }
}


@DefaultPreview
@Composable
private fun TownCollectionScreenPreview() {
    SolplyTheme {
        TownCollectionScreen(
            town = listOf(
                TownCard(
                    townName = "연희동",
                    imageUrl = "",
                ), TownCard(
                    townName = "망원동",
                    imageUrl = ""
                )
            ),
            onClickTown = { },
            mypageTab = MypageTab.COURSE,
            modifier = Modifier
        )
    }
}