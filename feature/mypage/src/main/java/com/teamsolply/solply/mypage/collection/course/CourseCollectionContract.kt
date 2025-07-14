package com.teamsolply.solply.mypage.collection.course

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.CourseCard
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class CourseCollectionState(
    val selectMode: Boolean = false,
    val town: String = "연희동",
    val courses: List<CourseCard> = immutableListOf(
        CourseCard(
            courseId = 0,
            courseName = "오감으로 수집하는 하루",
            placeTypeList = immutableListOf(
                PlaceType.CAFE,
                PlaceType.BOOK
            ),
            imageUrls = immutableListOf(1, 2, 3)
        ),
        CourseCard(
            courseId = 1,
            courseName = "오감으로 수집하는 하루",
            placeTypeList = immutableListOf(
                PlaceType.BOOK,
                PlaceType.CAFE
            ),
            imageUrls = immutableListOf(1, 2, 3)
        ),
        CourseCard(
            courseId = 2,
            courseName = "오감으로 수집하는 하루",
            placeTypeList = immutableListOf(
                PlaceType.SHOPPING,
                PlaceType.WALK
            ),
            imageUrls = immutableListOf(1, 2, 3)
        ),
        CourseCard(
            courseId = 3,
            courseName = "오감으로 수집하는 하루",
            placeTypeList = immutableListOf(
                PlaceType.FOOD,
                PlaceType.SHOPPING
            ),
            imageUrls = immutableListOf(1, 2, 3)
        ),
        CourseCard(
            courseId = 5,
            courseName = "오감으로 수집하는 하루",
            placeTypeList = immutableListOf(
                PlaceType.WALK,
                PlaceType.UNIQUE
            ),
            imageUrls = immutableListOf(1, 2, 3)
        )
    ),
    val selectedCourses: Set<Int> = emptySet(),
    val dialogState: Boolean = false
) : UiState

sealed interface CourseCollectionIntent : UiIntent {

    data object SelectButtonClick : CourseCollectionIntent
    data object DeleteButtonClick : CourseCollectionIntent
    data object CancelButtonClick : CourseCollectionIntent

    data object DialogConfirmClick : CourseCollectionIntent
    data object DialogDismissClick : CourseCollectionIntent

    data class CourseCardClick(val courseId: Int, val index: Int) : CourseCollectionIntent

    // Navigate
    data object BackButtonClick : CourseCollectionIntent
}

sealed interface CourseCollectionSideEffect : SideEffect {

    data object DeleteCourses : CourseCollectionSideEffect

    data object NavigateToBack : CourseCollectionSideEffect
    data object NavigateToMap : CourseCollectionSideEffect
}
