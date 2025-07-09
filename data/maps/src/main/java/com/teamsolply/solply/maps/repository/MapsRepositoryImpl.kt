package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.maps.model.NewCourseEntity
import com.teamsolply.solply.maps.model.PlaceInfo
import com.teamsolply.solply.maps.model.SnsLink
import com.teamsolply.solply.maps.source.MapsRemoteDataSource
import com.teamsolply.solply.model.PlaceType
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val mapsRemoteDataSource: MapsRemoteDataSource
) : MapsRepository {
    override suspend fun getPlaceInfo(placeId: Int): Result<PlaceInfo> = runCatching {
        PlaceInfo(
            placeId = 1,
            placeName = "유어마인드",
            primaryTag = PlaceType.CAFE,
            address = "서울 서대문구 연희로 189 - 16 단독주택 어쩌구",
            latitude = 37.4979,
            longitude = 127.0276,
            description = "귀여운 당고 디저트와 커피, 에이드가 있는 펫 프렌들리",
            imageInfos = listOf(

            ),
            contactNumber = "0507 - 1324 - 9018",
            openingHours = "월 - 금 10:00 - 19:00",
            isBookmarked = true,
            snsLink = listOf(
                SnsLink(
                    platform = "인스타그램",
                    url = "asd"
                )
            ),
            placeType = "SUBWAY_STATION",
            placeDefaultId = 222
        )

    }

    override suspend fun getAllCourseInfo(): Result<List<CourseInfo>> = runCatching {
        listOf(
            CourseInfo(
                courseId = 0,
                courseName = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 1,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 1,
                courseName = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 2,
                mainTag = listOf(PlaceType.BOOK, PlaceType.CAFE),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 2,
                courseName = "오감으로 수집하는 하루",
                placeCount = 5,
                thumbnailImage = 3,
                mainTag = listOf(PlaceType.UNIQUE, PlaceType.WALK),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 3,
                courseName = "오감으로 수집하는 하루",
                placeCount = 6,
                thumbnailImage = 4,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 4,
                courseName = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 5,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 5,
                courseName = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 6,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 6,
                courseName = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 7,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 7,
                courseName = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 8,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 8,
                courseName = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 9,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 9,
                courseName = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 10,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 10,
                courseName = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 11,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 11,
                courseName = "오감으로 수집하는 하루",
                placeCount = 5,
                thumbnailImage = 12,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfo(
                courseId = 12,
                courseName = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 13,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            )
        )
    }

    override suspend fun saveCourse(courseInfo: NewCourseEntity): Result<Unit> = runCatching {
        mapsRemoteDataSource.saveCourse(
            courseInfo = courseInfo.courseName
        )
    }
}
