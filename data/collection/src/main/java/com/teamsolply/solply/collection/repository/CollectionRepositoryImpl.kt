package com.teamsolply.solply.collection.repository

import android.util.Log
import com.teamsolply.solply.collection.model.CourseInfoEntity
import com.teamsolply.solply.collection.model.CourseTownEntity
import com.teamsolply.solply.collection.model.PlaceInfoEntity
import com.teamsolply.solply.collection.model.PlaceTownEntity
import com.teamsolply.solply.collection.source.CollectionRemoteDataSource
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionRemoteDataSource: CollectionRemoteDataSource
) : CollectionRepository {
    override suspend fun getPlaceTownList(): Result<List<PlaceTownEntity>> = runCatching {
        collectionRemoteDataSource.getPlaceTownList().townList
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
        Log.d("course town", "repository impl1 start")
        collectionRemoteDataSource.getCourseTownList().townList
    }.mapCatching { townList ->
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

    override suspend fun getPlaceList(townId: Long): Result<List<PlaceInfoEntity>> = runCatching {
        collectionRemoteDataSource.getPlaceList(townId).placeList
    }.mapCatching { placeList ->
        placeList.map { place ->
            PlaceInfoEntity(
                placeId = place.placeId,
                placeName = place.placeName,
                placeType = place.tag,
                imageUrls = place.imageUrl,
                isSaved = place.isSaved
            )
        }
    }

    override suspend fun getCourseList(townId: Long): Result<List<CourseInfoEntity>> = runCatching {
        collectionRemoteDataSource.getCourseList(townId).courseList
    }.mapCatching { courseList ->
        courseList.map { course ->
            CourseInfoEntity(
                courseId = course.courseId,
                courseName = course.title,
                placeTypeList = course.mainTags,
                imageUrls = listOf(course.imageUrl),
                isSaved = course.isSaved
            )
        }
    }

    override suspend fun deletePlaces(placeIds: List<Long>): Result<Unit> = runCatching {
        collectionRemoteDataSource.deletePlaces(placeIds)
    }

    override suspend fun deleteCourses(courseIds: List<Long>): Result<Unit> = runCatching {
        collectionRemoteDataSource.deleteCourses(courseIds)
    }
}
