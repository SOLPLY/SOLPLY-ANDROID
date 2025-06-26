package com.teamsolply.solply.course.source

interface CourseRemoteDataSource {
    suspend fun getRecommendedCourse(): String
}