package com.teamsolply.solply.course.service

import com.teamsolply.solply.course.dto.response.RecommendedCourseResponseDto
import com.teamsolply.solply.network.model.BaseResponse

interface CourseService {
    suspend fun getRecommendedCourse(): BaseResponse<RecommendedCourseResponseDto>
}
