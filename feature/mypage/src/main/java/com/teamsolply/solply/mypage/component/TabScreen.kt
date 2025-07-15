package com.teamsolply.solply.mypage.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.TownEntity
import com.teamsolply.solply.ui.preview.DefaultPreview
import okhttp3.internal.immutableListOf

@Composable
fun TabScreen(
    onClickEmptyButton: (MypageTab) -> Unit,
    onClickTown: (String) -> Unit,
    town: List<TownEntity>,
    mypageTab: MypageTab,
    modifier: Modifier = Modifier
) {
    if (town.isEmpty()) {
        EmptyCollectionScreen(
            onClick = { onClickEmptyButton(mypageTab) },
            mypageTab = mypageTab,
            modifier = modifier
        )
    } else {
        when (mypageTab) {
            MypageTab.PLACE ->
                TownCollectionScreen(
                    town = town,
                    onClickTown = onClickTown,
                    mypageTab = mypageTab,
                    modifier = modifier
                )

            MypageTab.COURSE ->
                TownCollectionScreen(
                    town = town,
                    onClickTown = onClickTown,
                    mypageTab = mypageTab,
                    modifier = modifier
                )
        }
    }
}

@DefaultPreview
@Composable
private fun PlaceTabScreenPreview() {
    SolplyTheme {
        TabScreen(
            onClickEmptyButton = {},
            town = immutableListOf(
                TownEntity(
                    townId = 1,
                    townName = "연희동",
                    tagList = listOf(
                        PlaceType.BOOK,
                        PlaceType.CAFE
                    ),
                    courseName = "오감으로 수집하는 코스",
                    imageUrl = ""
                ),
                TownEntity(
                    townId = 2,
                    townName = "망원동",
                    tagList = listOf(
                        PlaceType.WALK,
                        PlaceType.CAFE
                    ),
                    courseName = "오감으로 수집하는 코스",
                    imageUrl = ""
                ),
                TownEntity(
                    townId = 3,
                    townName = "성수동",
                    tagList = listOf(
                        PlaceType.FOOD,
                        PlaceType.CAFE
                    ),
                    courseName = "오감으로 수집하는 코스",
                    imageUrl = ""
                )
            ),
            onClickTown = {},
            mypageTab = MypageTab.COURSE
        )
    }
}
