package com.teamsolply.solply.course.service

import com.teamsolply.solply.course.dto.response.RecommendedCourseListResponseDto
import com.teamsolply.solply.course.dto.response.UserInfoResponseDto
import com.teamsolply.solply.network.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CourseService {
    @GET("api/users")
    suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto>

    @GET("api/recommend/courses")
    suspend fun getRecommendedCourseList(
        @Query("townId") townId: Int
    ): BaseResponse<RecommendedCourseListResponseDto>
}
