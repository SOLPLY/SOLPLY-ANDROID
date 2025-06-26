package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.model.NewCourseEntity

interface MapsRepository {
    suspend fun saveCourse(courseInfo: NewCourseEntity): Result<Unit>
}