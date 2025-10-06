package com.teamsolply.solply.network.service

import com.teamsolply.solply.network.model.NaverLocalSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverLocalSearchService {
    @GET("v1/search/local.json")
    suspend fun searchLocal(
        @Query("query") query: String,
        @Query("display") display: Int = 10,
        @Query("start") start: Int = 1,
        @Query("sort") sort: String = "random"
    ): NaverLocalSearchResponseDto
}