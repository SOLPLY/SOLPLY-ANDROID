package com.teamsolply.solply.place.source

import com.teamsolply.solply.place.dto.response.GetTagResponseDto

interface PlaceRemoteDataSource {
    suspend fun getTags(): List<GetTagResponseDto>
}
