package com.teamsolply.solply.place.service

import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.place.dto.response.GetRecommendPlaceListDto
import com.teamsolply.solply.place.dto.response.GetTagListResponseDto
import com.teamsolply.solply.place.dto.response.GetTagResponseDto
import com.teamsolply.solply.place.dto.response.GetUserInfoResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("/api/tags")
    suspend fun getTags(
        @Query("parentId") parentId: Int?
    ): BaseResponse<GetTagListResponseDto>

    @GET("/api/users")
    suspend fun getUserInfo(): BaseResponse<GetUserInfoResponseDto>

    @GET("/api/recommend/places")
    suspend fun getRecommendPlace(
        @Query("townId")
        townId: Long
    ): BaseResponse<GetRecommendPlaceListDto>
}
