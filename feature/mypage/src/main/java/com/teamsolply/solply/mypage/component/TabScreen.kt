package com.teamsolply.solply.mypage.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.CourseTownEntity
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.PlaceTownEntity
import com.teamsolply.solply.ui.preview.DefaultPreview
import okhttp3.internal.immutableListOf

@Composable
fun TabScreen(
    onClickEmptyButton: (MypageTab) -> Unit,
    onClickTown: (String) -> Unit,
    placeTown: List<PlaceTownEntity>,
    courseTown: List<CourseTownEntity>,
    mypageTab: MypageTab,
    modifier: Modifier = Modifier
) {
    when (mypageTab) {
        MypageTab.PLACE ->
            if (placeTown.isEmpty()) {
                EmptyCollectionScreen(
                    onClick = { onClickEmptyButton(mypageTab) },
                    mypageTab = mypageTab,
                    modifier = modifier
                )
            } else {
                PlaceTownCollectionScreen(
                    town = placeTown,
                    onClickTown = onClickTown,
                    modifier = modifier
                )
            }

        MypageTab.COURSE ->
            if (courseTown.isEmpty()) {
                EmptyCollectionScreen(
                    onClick = { onClickEmptyButton(mypageTab) },
                    mypageTab = mypageTab,
                    modifier = modifier
                )
            } else {
                CourseTownCollectionScreen(
                    town = courseTown,
                    onClickTown = onClickTown,
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
            placeTown = immutableListOf(
                PlaceTownEntity(
                    townId = 1,
                    townName = "연희동",
                    imageUrl = ""
                ),
                PlaceTownEntity(
                    townId = 2,
                    townName = "망원동",
                    imageUrl = ""
                ),
                PlaceTownEntity(
                    townId = 3,
                    townName = "성수동",
                    imageUrl = ""
                )
            ),
            courseTown = immutableListOf(
                CourseTownEntity(
                    townId = 1,
                    townName = "연희동",
                    tagList = listOf(
                        PlaceType.BOOK,
                        PlaceType.CAFE
                    ),
                    courseName = "오감으로 수집하는 코스",
                    imageUrl = ""
                ),
                CourseTownEntity(
                    townId = 2,
                    townName = "망원동",
                    tagList = listOf(
                        PlaceType.WALK,
                        PlaceType.CAFE
                    ),
                    courseName = "오감으로 수집하는 코스",
                    imageUrl = ""
                ),
                CourseTownEntity(
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
