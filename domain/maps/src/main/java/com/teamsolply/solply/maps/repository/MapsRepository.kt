package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.PlaceDetailEntity

interface MapsRepository {
    // Add Place
    suspend fun getPlaceDetail(placeId: Long): Result<PlaceDetailEntity>
    suspend fun savePlaceBookMark(placeId: Long): Result<Unit>
    suspend fun deletePlaceBookMark(placeId: Long): Result<Unit>

    suspend fun getAllCourses(): Result<List<CourseInfoEntity>>

    // Add Course
    suspend fun getCourseDetail(courseId: Long): Result<CourseDetailEntity>
}
