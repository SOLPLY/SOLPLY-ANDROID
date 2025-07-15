package com.teamsolply.solply.mypage

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.CourseTownEntity
import com.teamsolply.solply.mypage.model.PlaceTownEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class MypageState(
    val placeTowns: List<PlaceTownEntity> =
        immutableListOf(
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
    val courseTowns: List<CourseTownEntity> =
//        emptyList<TownCard>(),
        immutableListOf(
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
    val town: String = "연희동",
    val selectedTab: MypageTab = MypageTab.PLACE,
    val isPlaceTownSelected: Boolean = false,
    val isCourseTownSelected: Boolean = false
) : UiState

sealed interface MypageIntent : UiIntent {
    // TODO 탭 이중관리
    data object SelectPlaceTab : MypageIntent
    data object SelectCourseTab : MypageIntent

    //
    data class SelectPlaceTown(val selectedTown: String) : MypageIntent
    data class SelectCourseTown(val selectedTown: String) : MypageIntent

    data class EmptyButtonClick(val mypageTab: MypageTab) : MypageIntent

    // Navigate
    data object BackButtonClick : MypageIntent
}

sealed interface MypageSideEffect : SideEffect {
    data object NavigateToBack : MypageSideEffect
    data object NavigateToPLace : MypageSideEffect
    data object NavigateToCourse : MypageSideEffect
    data class NavigateToPlaceCollection(val town: String) : MypageSideEffect
    data class NavigateToCourseCollection(val town: String) : MypageSideEffect
}
