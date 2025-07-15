package com.teamsolply.solply.mypage.service

import com.teamsolply.solply.mypage.dto.response.PlaceTownListResponseDto
import com.teamsolply.solply.network.model.BaseResponse
import retrofit2.http.GET

interface MypageService {
    @GET("api/places/bookmarks/folders/preview")
    suspend fun getPlaceTownList(): BaseResponse<PlaceTownListResponseDto>
    suspend fun getCourseTownList()
    suspend fun getPlaceList()
    suspend fun getCourseList()

    suspend fun deletePlaces(placeIds: List<Int>)
    suspend fun deleteCourses(courseIds: List<Int>)
}
