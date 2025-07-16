package com.teamsolply.solply.place.source

import com.teamsolply.solply.place.dto.response.GetRecommendPlaceDto
import com.teamsolply.solply.place.dto.response.GetTagResponseDto
import com.teamsolply.solply.place.dto.response.GetUserInfoResponseDto

interface PlaceRemoteDataSource {
    suspend fun getTags(parentId: Int?): List<GetTagResponseDto>
    suspend fun getUserInfo(): GetUserInfoResponseDto
    suspend fun getRecommendPlace(townId: Long): List<GetRecommendPlaceDto>
}
