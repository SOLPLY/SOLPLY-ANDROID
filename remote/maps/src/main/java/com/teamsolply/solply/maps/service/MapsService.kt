package com.teamsolply.solply.maps.service

import com.teamsolply.solply.maps.dto.request.CourseSaveRequestDto
import com.teamsolply.solply.maps.dto.response.CourseDetailResponseDto
import com.teamsolply.solply.maps.dto.response.CourseSaveResponseDto
import com.teamsolply.solply.maps.dto.response.CoursesResponseDto
import com.teamsolply.solply.maps.dto.response.GetPlaceDetailResponseDto
import com.teamsolply.solply.maps.dto.response.NewCourseResponseDto
import com.teamsolply.solply.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MapsService {
    // 장소 상세

    // 장소 단건 정보
    @GET("api/places/{placeId}")
    suspend fun getPlaceDetail(
        @Path("placeId") placeId: Long
    ): BaseResponse<GetPlaceDetailResponseDto>

    // 장소 단건 북마크 저장
    @POST("api/places/{placeId}/bookmarks")
    suspend fun postPlaceBookMark(
        @Path("placeId") placeId: Long
    ): BaseResponse<Unit>

    // 장소 단건 북마크 삭제
    @DELETE("api/places/{placeId}/bookmarks")
    suspend fun deletePlaceBookMark(
        @Path("placeId") placeId: Long
    ): BaseResponse<Unit>

    // 내 코스에 추가 >
    @GET("api/courses/bookmarks")
    suspend fun getAddMyCourse(
        @Query("townId") townId: Long,
        @Query("placeId") placeId: Long
    ): BaseResponse<CoursesResponseDto>

    // 내 코스에 추가

    // 코스 단건 상세 조회
    @GET("api/courses/{courseId}")
    suspend fun getCourseDetail(
        @Path("courseId") courseId: Long
    ): BaseResponse<CourseDetailResponseDto>

    // 코스 단건 북마크 저장
    @POST("api/courses/{courseId}/bookmarks")
    suspend fun postCourseBookMark(
        @Path("courseId") courseId: Long
    ): BaseResponse<Unit>

    // 코스 단건 북마크 삭제
    @DELETE("api/courses/{courseId}/bookmarks")
    suspend fun deleteCourseBookMark(
        @Path("courseId") courseId: Long
    ): BaseResponse<Unit>

    // 코스 편집

    @PUT("api/courses/{courseId}")
    suspend fun putEditCourse(
        @Path("courseId") courseId: Long,
        @Body courseSaveRequestDto: CourseSaveRequestDto
    ): BaseResponse<CourseSaveResponseDto>

    @POST("api/courses")
    suspend fun postSaveNewCourse(
        @Body courseSaveRequestDto: CourseSaveRequestDto
    ): BaseResponse<NewCourseResponseDto>
}
