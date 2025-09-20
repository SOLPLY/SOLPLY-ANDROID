package com.teamsolply.solply.collection.repository

import com.teamsolply.solply.collection.model.CourseInfoEntity
import com.teamsolply.solply.collection.model.CourseTownEntity
import com.teamsolply.solply.collection.model.PlaceInfoEntity
import com.teamsolply.solply.collection.model.PlaceTownEntity

interface CollectionRepository {
    suspend fun getPlaceTownList(): Result<List<PlaceTownEntity>>
    suspend fun getCourseTownList(): Result<List<CourseTownEntity>>

    suspend fun getPlaceList(townId: Long): Result<List<PlaceInfoEntity>>
    suspend fun getCourseList(townId: Long): Result<List<CourseInfoEntity>>

    suspend fun deletePlaces(placeIds: List<Long>): Result<Unit>
    suspend fun deleteCourses(courseIds: List<Long>): Result<Unit>
}
