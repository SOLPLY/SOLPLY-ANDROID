package com.teamsolply.solply.course.repository

import com.teamsolply.solply.course.model.CourseEntity

interface CourseRepository {
    suspend fun getRecommendedCourse(): Result<CourseEntity>
}