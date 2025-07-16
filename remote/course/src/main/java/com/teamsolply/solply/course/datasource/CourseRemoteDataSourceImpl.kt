package com.teamsolply.solply.course.datasource

import android.util.Log
import com.teamsolply.solply.course.dto.response.RecommendedCourseListResponseDto
import com.teamsolply.solply.course.dto.response.RecommendedCourseResponseDto
import com.teamsolply.solply.course.dto.response.UserInfoResponseDto
import com.teamsolply.solply.course.service.CourseService
import com.teamsolply.solply.course.source.CourseRemoteDataSource
import javax.inject.Inject

class CourseRemoteDataSourceImpl @Inject constructor(
    private val courseService: CourseService
) : CourseRemoteDataSource {
    override suspend fun getUserInfo(): UserInfoResponseDto {
        try {
            return courseService.getUserInfo().data
        } catch (e: Exception) {
            Log.e("courselist", e.toString())
            return courseService.getUserInfo().data
        }
    }

    override suspend fun getRecommendedCourse(townId: Int): RecommendedCourseListResponseDto {
        try {
            return courseService.getRecommendedCourseList(townId).data
        } catch (e: Exception) {
            Log.e("courselist", e.toString())
            return courseService.getRecommendedCourseList(townId).data
        }
    }
}
