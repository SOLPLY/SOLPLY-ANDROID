package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.Place
import com.teamsolply.solply.maps.model.PlaceDetailEntity
import com.teamsolply.solply.maps.source.MapsRemoteDataSource
import com.teamsolply.solply.model.PlaceType
import toEntity
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val mapsRemoteDataSource: MapsRemoteDataSource
) : MapsRepository {
    // Add Place
    override suspend fun getPlaceDetail(placeId: Long): Result<PlaceDetailEntity> = runCatching {
        mapsRemoteDataSource.getPlaceDetail(placeId = placeId)
    }.mapCatching { responseDto ->
        responseDto.toEntity()
    }

    override suspend fun savePlaceBookMark(placeId: Long): Result<Unit> = runCatching {
        mapsRemoteDataSource.savePlaceBookMark(placeId = placeId)
    }

    override suspend fun deletePlaceBookMark(placeId: Long): Result<Unit> = runCatching {
        mapsRemoteDataSource.deletePlaceBookMark(placeId = placeId)
    }


    override suspend fun getAllCourses(): Result<List<CourseInfoEntity>> = runCatching {
        listOf(
            CourseInfoEntity(
                courseId = 0,
                title = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 1,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 1,
                title = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 2,
                mainTag = listOf(PlaceType.BOOK, PlaceType.CAFE),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 2,
                title = "오감으로 수집하는 하루",
                placeCount = 5,
                thumbnailImage = 3,
                mainTag = listOf(PlaceType.UNIQUE, PlaceType.WALK),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 3,
                title = "오감으로 수집하는 하루",
                placeCount = 6,
                thumbnailImage = 4,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 4,
                title = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 5,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 5,
                title = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 6,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 6,
                title = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 7,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 7,
                title = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 8,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 8,
                title = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 9,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 9,
                title = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 10,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 10,
                title = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 11,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 11,
                title = "오감으로 수집하는 하루",
                placeCount = 5,
                thumbnailImage = 12,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            ),
            CourseInfoEntity(
                courseId = 12,
                title = "오감으로 수집하는 하루",
                placeCount = 2,
                thumbnailImage = 13,
                mainTag = listOf(PlaceType.FOOD, PlaceType.SHOPPING),
                isBookmarked = true,
                isActive = true
            )
        )
    }

    // Add Course
    override suspend fun getCourseInfo(courseId: Int): Result<CourseDetailEntity> = runCatching {
        CourseDetailEntity(
            courseId = 1,
            courseName = "오감으로 수집하는 하루",
            introduction = "귀여운 당고 디저트와 ...",
            isBookmarked = true,
            places = listOf(
                Place(
                    placeId = 1,
                    placeName = "유어마인드",
                    thumbnailUrl = "",
                    primaryTag = "카페",
                    address = "서울 서대문구 연희로11라길 10-6",
                    isBookmarked = true,
                    placeOrder = 1,
                    latitude = "37.4979",
                    longitude = "127.0276",
                    placeTag = "shopping",
                    placeDefaultId = 123
                ),
                Place(
                    placeId = 2,
                    placeName = "비스트롤 연회",
                    thumbnailUrl = "",
                    primaryTag = "카페",
                    address = "서울 서대문구 연희로11라길 10-6",
                    isBookmarked = false,
                    placeOrder = 2,
                    latitude = "37.4999",
                    longitude = "127.0286",
                    placeTag = "shopping",
                    placeDefaultId = 123
                ),
                Place(
                    placeId = 3,
                    placeName = "파파연회",
                    thumbnailUrl = "",
                    primaryTag = "카페",
                    address = "서울 서대문구 연희로11라길 10-6",
                    isBookmarked = false,
                    placeOrder = 3,
                    latitude = "37.4999",
                    longitude = "127.0376",
                    placeTag = "shopping",
                    placeDefaultId = 123
                ),
                Place(
                    placeId = 4,
                    placeName = "라이카시네마",
                    thumbnailUrl = "",
                    primaryTag = "카페",
                    address = "서울 서대문구 연희로11라길 10-6",
                    isBookmarked = false,
                    placeOrder = 4,
                    latitude = "37.4980",
                    longitude = "127.0226",
                    placeTag = "shopping",
                    placeDefaultId = 123
                ),
                Place(
                    placeId = 5,
                    placeName = "아아아아",
                    thumbnailUrl = "",
                    primaryTag = "카페",
                    address = "서울 서대문구 연희로11라길 10-6",
                    isBookmarked = true,
                    placeOrder = 5,
                    latitude = "37.4999",
                    longitude = "127.0226",
                    placeTag = "shopping",
                    placeDefaultId = 123
                )
            )
        )
    }
}
