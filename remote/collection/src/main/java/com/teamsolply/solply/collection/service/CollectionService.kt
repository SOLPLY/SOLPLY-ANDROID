package com.teamsolply.solply.collection.service

import com.teamsolply.solply.collection.dto.response.CourseListResponseDto
import com.teamsolply.solply.collection.dto.response.CourseTownListResponseDto
import com.teamsolply.solply.collection.dto.response.PlaceListResponseDto
import com.teamsolply.solply.collection.dto.response.PlaceTownListResponseDto
import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.network.model.NullableBaseResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface CollectionService {
    @GET("api/places/bookmarks/folders/preview")
    suspend fun getPlaceTownList(): BaseResponse<PlaceTownListResponseDto>

    @GET("api/courses/bookmarks/folders")
    suspend fun getCourseTownList(): BaseResponse<CourseTownListResponseDto>

    @GET("api/places")
    suspend fun getPlaceList(
        @Query("townId") townId: Long,
        @Query("isBookmarkSearch") isBookmarkedSearch: Boolean = true
    ): BaseResponse<PlaceListResponseDto>

    @GET("api/courses/bookmarks")
    suspend fun getCourseList(
        @Query("townId") townId: Long
    ): BaseResponse<CourseListResponseDto>

    @DELETE("api/places/bookmarks")
    suspend fun deletePlaces(
        @Query(value = "placeIds") placeIds: List<Long>
    ): NullableBaseResponse<Unit>

    @DELETE("api/courses/bookmarks")
    suspend fun deleteCourses(
        @Query(value = "courseIds") courseIds: List<Long>
    ): NullableBaseResponse<Unit>
}
