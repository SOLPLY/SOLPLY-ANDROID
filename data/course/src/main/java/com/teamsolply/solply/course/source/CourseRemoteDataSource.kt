package com.teamsolply.solply.course.source

import com.teamsolply.solply.course.dto.response.RecommendedCourseListResponseDto
import com.teamsolply.solply.course.dto.response.UserInfoResponseDto

interface CourseRemoteDataSource {
    suspend fun getRecommendedCourse(townId: Long): RecommendedCourseListResponseDto
    suspend fun getUserInfo(): UserInfoResponseDto
}
