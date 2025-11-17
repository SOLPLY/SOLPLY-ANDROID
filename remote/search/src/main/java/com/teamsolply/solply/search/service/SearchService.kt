package com.teamsolply.solply.search.service

import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.search.dto.request.RegisterPlaceRequestDto
import com.teamsolply.solply.search.dto.response.RegisterPlaceResponseDto
import com.teamsolply.solply.search.dto.response.SearchResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchService {
    @GET("/api/places/search")
    suspend fun getPlaceSearch(
        @Query("keyword") keyword: String
    ): BaseResponse<SearchResponseDto>

    @POST("/api/places/requests")
    suspend fun requestsPlaces(
        @Body registerPlaceRequestDto: RegisterPlaceRequestDto
    ): BaseResponse<RegisterPlaceResponseDto>
}
