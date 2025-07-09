package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.maps.model.NewCourseEntity
import com.teamsolply.solply.maps.model.PlaceInfo

interface MapsRepository {
    suspend fun getPlaceInfo(placeId: Int): Result<PlaceInfo>
    suspend fun getAllCourseInfo(): Result<List<CourseInfo>>
    suspend fun saveCourse(courseInfo: NewCourseEntity): Result<Unit>
}
