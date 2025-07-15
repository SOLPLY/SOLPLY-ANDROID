package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.CourseInfoEntity
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.mypage.model.CourseTownEntity
import com.teamsolply.solply.mypage.model.PlaceTownEntity
import com.teamsolply.solply.mypage.source.MypageRemoteDataSource
import okhttp3.internal.immutableListOf
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageRemoteDataSource: MypageRemoteDataSource
) : MypageRepository {
    override suspend fun getPlaceTownList(): Result<List<PlaceTownEntity>> = runCatching {
        mypageRemoteDataSource.getPlaceTownList()
        listOf(
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
        )
    }

    override suspend fun getCourseTownList(): Result<List<CourseTownEntity>> = runCatching {
        mypageRemoteDataSource.getCourseTownList()
        listOf(
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
        )
    }

    override suspend fun getPlaceList(): Result<List<PlaceInfoEntity>> = runCatching {
        mypageRemoteDataSource.getPlaceList()
        listOf(
            PlaceInfoEntity(
                placeId = 0,
                placeName = "0번",
                placeType = PlaceType.CAFE,
                imageUrls = listOf()
            ),
            PlaceInfoEntity(
                placeId = 1,
                placeName = "1번",
                placeType = PlaceType.BOOK,
                imageUrls = listOf()
            ),
            PlaceInfoEntity(
                placeId = 2,
                placeName = "2번",
                placeType = PlaceType.SHOPPING,
                imageUrls = listOf()
            ),
            PlaceInfoEntity(
                placeId = 3,
                placeName = "3번",
                placeType = PlaceType.FOOD,
                imageUrls = listOf()
            ),
            PlaceInfoEntity(
                placeId = 4,
                placeName = "3번",
                placeType = PlaceType.FOOD,
                imageUrls = listOf()
            ),
            PlaceInfoEntity(
                placeId = 5,
                placeName = "3번",
                placeType = PlaceType.FOOD,
                imageUrls = listOf()
            ),
            PlaceInfoEntity(
                placeId = 6,
                placeName = "3번",
                placeType = PlaceType.FOOD,
                imageUrls = listOf()
            ),
            PlaceInfoEntity(
                placeId = 7,
                placeName = "3번",
                placeType = PlaceType.FOOD,
                imageUrls = listOf()
            ),
            PlaceInfoEntity(
                placeId = 8,
                placeName = "3번",
                placeType = PlaceType.FOOD,
                imageUrls = listOf()
            )
        )
    }

    override suspend fun getCourseList(): Result<List<CourseInfoEntity>> = runCatching {
        mypageRemoteDataSource.getCourseList()
        listOf(
            CourseInfoEntity(
                courseId = 0,
                courseName = "오감으로 수집하는 하루",
                placeTypeList = immutableListOf(
                    PlaceType.CAFE,
                    PlaceType.BOOK
                ),
                imageUrls = immutableListOf(1, 2, 3)
            ),
            CourseInfoEntity(
                courseId = 1,
                courseName = "오감으로 수집하는 하루",
                placeTypeList = immutableListOf(
                    PlaceType.BOOK,
                    PlaceType.CAFE
                ),
                imageUrls = immutableListOf(1, 2, 3)
            ),
            CourseInfoEntity(
                courseId = 2,
                courseName = "오감으로 수집하는 하루",
                placeTypeList = immutableListOf(
                    PlaceType.SHOPPING,
                    PlaceType.WALK
                ),
                imageUrls = immutableListOf(1, 2, 3)
            ),
            CourseInfoEntity(
                courseId = 3,
                courseName = "오감으로 수집하는 하루",
                placeTypeList = immutableListOf(
                    PlaceType.FOOD,
                    PlaceType.SHOPPING
                ),
                imageUrls = immutableListOf(1, 2, 3)
            ),
            CourseInfoEntity(
                courseId = 5,
                courseName = "오감으로 수집하는 하루",
                placeTypeList = immutableListOf(
                    PlaceType.WALK,
                    PlaceType.UNIQUE
                ),
                imageUrls = immutableListOf(1, 2, 3)
            )
        )
    }

    override suspend fun deletePlaces(placeIds: List<Int>): Result<Unit> = runCatching {
        mypageRemoteDataSource.deletePlaces(placeIds)
    }

    override suspend fun deleteCourses(courseIds: List<Int>): Result<Unit> = runCatching {
        mypageRemoteDataSource.deleteCourses(courseIds)
    }
}

