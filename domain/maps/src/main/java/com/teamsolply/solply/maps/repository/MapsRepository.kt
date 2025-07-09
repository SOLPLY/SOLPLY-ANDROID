package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.NewCourseEntity
import com.teamsolply.solply.maps.model.PlaceDetailEntity

interface MapsRepository {
    //Add Place
    suspend fun getPlaceInfo(placeId: Int): Result<PlaceDetailEntity>
    suspend fun getAllCourses(): Result<List<CourseInfoEntity>>

    //Add Course
    suspend fun getCourseInfo(courseId: Int): Result<CourseDetailEntity>
    suspend fun saveCourse(courseInfo: NewCourseEntity): Result<Unit>
}
