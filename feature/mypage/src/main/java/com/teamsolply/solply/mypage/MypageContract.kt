package com.teamsolply.solply.mypage

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.PlaceCard
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class MypageState(
    val places: List<PlaceCard> = immutableListOf(
        PlaceCard(
            placeId = 0,
            placeName = "0번",
            placeType = PlaceType.CAFE,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
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
        ),
        PlaceCard(
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceCard(
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceCard(
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceCard(
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
        PlaceCard(
            placeId = 3,
            placeName = "3번",
            placeType = PlaceType.FOOD,
            imageUrls = listOf(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy)
        ),
    ),
    val selectedTab: MypageTab = MypageTab.PLACE,
) : UiState

sealed interface MypageIntent : UiIntent {
    // TODO 탭 이중관리

    // Navigate
    data object BackButtonClick : MypageIntent
}

sealed interface MypageSideEffect : SideEffect {
    data object NavigateToBack : MypageSideEffect
}