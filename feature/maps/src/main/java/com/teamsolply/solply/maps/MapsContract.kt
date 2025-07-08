package com.teamsolply.solply.maps

import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.maps.model.PlaceInfo
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class MapsState(
    // Add Place
    val placeInfo: PlaceInfo = PlaceInfo(
        placeId = 0,
        placeName = "",
        primaryTag = PlaceType.EMPTY,
        address = "",
        priority = 0,
        latitude = 37.4979,
        longitude = 127.0276,
        description = "",
        imageUrls = listOf(
            com.teamsolply.solply.designsystem.R.drawable.img_place_img_dummy,
            com.teamsolply.solply.designsystem.R.drawable.img_place_img_dummy,
            com.teamsolply.solply.designsystem.R.drawable.img_place_img_dummy,
        ),
        contactNumber = "",
        openingHours = "",
        snsLink = "",
        isBookmarked = true
    ),
    val courses: List<CourseInfo> = listOf(
        CourseInfo(
            courseId = 0,
            title = "오감으로 수집하는 하루",
            placeCount = 2,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 1,
            title = "오감으로 수집하는 하루",
            placeCount = 2,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.BOOK, PlaceType.CAFE),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 2,
            title = "오감으로 수집하는 하루",
            placeCount = 5,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.UNIQUE, PlaceType.WALK),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 3,
            title = "오감으로 수집하는 하루",
            placeCount = 6,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 4,
            title = "오감으로 수집하는 하루",
            placeCount = 2,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 5,
            title = "오감으로 수집하는 하루",
            placeCount = 2,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 6,
            title = "오감으로 수집하는 하루",
            placeCount = 2,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 7,
            title = "오감으로 수집하는 하루",
            placeCount = 2,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 8,
            title = "오감으로 수집하는 하루",
            placeCount = 2,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 9,
            title = "오감으로 수집하는 하루",
            placeCount = 2,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 10,
            title = "오감으로 수집하는 하루",
            placeCount = 2,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 11,
            title = "오감으로 수집하는 하루",
            placeCount = 5,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
        CourseInfo(
            courseId = 12,
            title = "오감으로 수집하는 하루",
            placeCount = 2,
            thumbnailImage = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
            isBookmarked = true,
            isActive = true
        ),
    ),
    val startAddMyCourse: Boolean = false,
    val addMyCourseSelectedCount: List<Int> = emptyList(),
    // Add Course
    // Edit Course
    val course: List<PlaceInfo> = immutableListOf(
        PlaceInfo(
            placeId = 0,
            placeName = "0번",
            primaryTag = PlaceType.CAFE,
            address = "주소",
            priority = 0,
            latitude = 37.4979,
            longitude = 127.0276,
            description = "귀여운 당고 디저트 카페, 에이드가 있는 펫 프랜들리 카페",
            imageUrls = listOf(
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy
            ),
            contactNumber = "0507-1324-9018",
            openingHours = "월-금 10:00 - 19:00",
            snsLink = "인스타그램",
            isBookmarked = true
        ),
        PlaceInfo(
            placeId = 1,
            placeName = "1번",
            primaryTag = PlaceType.BOOK,
            address = "주소",
            priority = 1,
            latitude = 37.4999,
            longitude = 127.0286,
            description = "귀여운 당고 디저트 카페, 에이드가 있는 펫 프랜들리 카페",
            imageUrls = listOf(
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy
            ),
            contactNumber = "0507-1324-9018",
            openingHours = "월-금 10:00 - 19:00",
            snsLink = "인스타그램",
            isBookmarked = true
        ),
        PlaceInfo(
            placeId = 2,
            placeName = "2번",
            primaryTag = PlaceType.UNIQUE,
            address = "주소",
            priority = 2,
            latitude = 37.4999,
            longitude = 127.0376,
            description = "귀여운 당고 디저트 카페, 에이드가 있는 펫 프랜들리 카페",
            imageUrls = listOf(
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy
            ),
            contactNumber = "0507-1324-9018",
            openingHours = "월-금 10:00 - 19:00",
            snsLink = "인스타그램",
            isBookmarked = true
        ),
        PlaceInfo(
            placeId = 3,
            placeName = "3번",
            primaryTag = PlaceType.FOOD,
            address = "주소",
            priority = 3,
            latitude = 37.4991,
            longitude = 127.0255,
            description = "귀여운 당고 디저트 카페, 에이드가 있는 펫 프랜들리 카페",
            imageUrls = listOf(
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy
            ),
            contactNumber = "0507-1324-9018",
            openingHours = "월-금 10:00 - 19:00",
            snsLink = "인스타그램",
            isBookmarked = true
        ),
        PlaceInfo(
            placeId = 4,
            placeName = "4번",
            primaryTag = PlaceType.SHOPPING,
            address = "주소",
            priority = 4,
            latitude = 37.4980,
            longitude = 127.0226,
            description = "귀여운 당고 디저트 카페, 에이드가 있는 펫 프랜들리 카페",
            imageUrls = listOf(
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy
            ),
            contactNumber = "0507-1324-9018",
            openingHours = "월-금 10:00 - 19:00",
            snsLink = "인스타그램",
            isBookmarked = true
        ),
        PlaceInfo(
            placeId = 5,
            placeName = "5번",
            primaryTag = PlaceType.BOOK,
            address = "주소",
            priority = 5,
            latitude = 37.4999,
            longitude = 127.0226,
            description = "귀여운 당고 디저트 카페, 에이드가 있는 펫 프랜들리 카페",
            imageUrls = listOf(
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                com.teamsolply.solply.designsystem.R.drawable.img_course_dummy
            ),
            contactNumber = "0507-1324-9018",
            openingHours = "월-금 10:00 - 19:00",
            snsLink = "인스타그램",
            isBookmarked = true
        )
    ),
    val iconVisibility: Boolean = false
) : UiState

sealed interface MapsIntent : UiIntent {
    //Add Place
    data class AddPlaceClick(
        val addPlace: Boolean
    ) : MapsIntent

    data class SelectedCourseForPlace(
        val courseId: Int
    ) : MapsIntent

    data object SaveMyCourse : MapsIntent

    //Edit Course
    // Item Drag and Remove
    data class StartCourseMove(
        val iconVisibility: Boolean
    ) : MapsIntent

    data class MoveCourseItem(
        val fromIndex: Int,
        val toIndex: Int
    ) : MapsIntent

    data class RemoveCourseItem(
        val itemToRemove: Int
    ) : MapsIntent

    // Navigate
    data object ShowMaxSizeCourseSnackBar : MapsIntent
    data object ReturnToHomeClick : MapsIntent
    data object BackButtonClick : MapsIntent
}

sealed interface MapsSideEffect : SideEffect {
    //Add Place
    data object ShowMaxSizeCourseSnackBar : MapsSideEffect
    data class ShowSuccessSaveCourseSnackBar(val selectedCourseName: String) : MapsSideEffect

    //Edit Course
    data object DisabledRemoveCourse : MapsSideEffect

    //Shared
    data object NavigateToReturnHome : MapsSideEffect
    data object NavigateToBack : MapsSideEffect
}
