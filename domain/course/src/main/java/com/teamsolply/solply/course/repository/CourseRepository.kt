package com.teamsolply.solply.course.repository

import com.teamsolply.solply.course.model.CourseEntity
import com.teamsolply.solply.course.model.UserEntity

interface CourseRepository {
    suspend fun getUserInfo(): Result<UserEntity>
    suspend fun getRecommendedCourse(townId: Int): Result<List<CourseEntity>>
}
