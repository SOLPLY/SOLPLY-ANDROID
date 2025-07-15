package com.teamsolply.solply.mypage.service

import com.teamsolply.solply.mypage.dto.response.CourseListResponseDto
import com.teamsolply.solply.mypage.dto.response.CourseTownListResponseDto
import com.teamsolply.solply.mypage.dto.response.PlaceListResponseDto
import com.teamsolply.solply.mypage.dto.response.PlaceTownListResponseDto
import com.teamsolply.solply.network.model.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface MypageService {
    @GET("api/places/bookmarks/folders/preview")
    suspend fun getPlaceTownList(): BaseResponse<PlaceTownListResponseDto>

    @GET("/api/courses/bookmarks/folders")
    suspend fun getCourseTownList(): BaseResponse<CourseTownListResponseDto>

    @GET("/api/places")
    suspend fun getPlaceList(): BaseResponse<PlaceListResponseDto>

    @GET("/api/courses/bookmarks")
    suspend fun getCourseList(): BaseResponse<CourseListResponseDto>

    @DELETE("/api/places/bookmarks")
    suspend fun deletePlaces(
        @Query(value = "placeIds") placeIds: List<Int>
    )

    @DELETE("/api/courses/bookmarks")
    suspend fun deleteCourses(
        @Query(value = "courseIds") courseIds: List<Int>
    )
}
