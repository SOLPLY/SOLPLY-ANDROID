package com.teamsolply.solply.place.service

import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.place.dto.response.GetTagResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("/api/tags?parentId")
    suspend fun getTags(
        @Query("parentId")
        parentId: Int?
    ): BaseResponse<List<GetTagResponseDto>>
}
