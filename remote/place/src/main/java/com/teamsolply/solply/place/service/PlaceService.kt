package com.teamsolply.solply.place.service

import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.place.dto.response.RecommendedPlaceResponseDto

interface PlaceService {
    suspend fun getRecommendedPlace(): BaseResponse<RecommendedPlaceResponseDto>
}