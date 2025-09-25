package com.teamsolply.solply.search.service

import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.search.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/api/places/search")
    suspend fun getPlaceSearch(
        @Query("keyword") keyword: String
    ): BaseResponse<SearchResponseDto>
}
