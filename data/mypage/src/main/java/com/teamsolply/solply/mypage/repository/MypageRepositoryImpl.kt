package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.mypage.model.CourseInfoEntity
import com.teamsolply.solply.mypage.model.CourseTownEntity
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.mypage.model.PlaceTownEntity
import com.teamsolply.solply.mypage.source.MypageRemoteDataSource
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageRemoteDataSource: MypageRemoteDataSource
) : MypageRepository {
    override suspend fun getPlaceTownList(): Result<List<PlaceTownEntity>> = runCatching {
        mypageRemoteDataSource.getPlaceTownList().townList
    }.mapCatching { townList ->
        townList.map { town ->
            PlaceTownEntity(
                townId = town.townId,
                townName = town.townName,
                imageUrl = town.imageUrl
            )
        }
    }

    override suspend fun getCourseTownList(): Result<List<CourseTownEntity>> = runCatching {
        mypageRemoteDataSource.getCourseTownList().townList
    }.map { townList ->
        townList.map { town ->
            CourseTownEntity(
                townId = town.townId,
                townName = town.townName,
                imageUrl = town.imageUrl,
                courseName = town.courseName,
                tagList = town.tagList
            )
        }

    }

    override suspend fun getPlaceList(): Result<List<PlaceInfoEntity>> = runCatching {
        mypageRemoteDataSource.getPlaceList().placeList
    }.mapCatching { placeList ->
        placeList.map { place ->
            PlaceInfoEntity(
                placeId = place.placeId,
                placeName = place.placeName,
                placeType = place.tag,
                imageUrls = place.imageUrl
            )
        }
    }

    override suspend fun getCourseList(): Result<List<CourseInfoEntity>> = runCatching {
        mypageRemoteDataSource.getCourseList().courseList
    }.mapCatching { courseList ->
        courseList.map { course ->
            CourseInfoEntity(
                courseId = course.courseId,
                courseName = course.courseName,
                placeTypeList = listOf(course.tag),
                imageUrls = listOf(course.imageUrl),
            )
        }
    }

    override suspend fun deletePlaces(placeIds: List<Int>): Result<Unit> = runCatching {
        mypageRemoteDataSource.deletePlaces(placeIds)
    }

    override suspend fun deleteCourses(courseIds: List<Int>): Result<Unit> = runCatching {
        mypageRemoteDataSource.deleteCourses(courseIds)
    }
}
