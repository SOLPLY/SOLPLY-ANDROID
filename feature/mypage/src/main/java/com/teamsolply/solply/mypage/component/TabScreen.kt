package com.teamsolply.solply.mypage.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teamsolply.solply.mypage.model.CourseTownEntity
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.PlaceTownEntity

@Composable
fun TabScreen(
    onClickEmptyButton: (MypageTab) -> Unit,
    onClickTown: (Int, String) -> Unit,
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