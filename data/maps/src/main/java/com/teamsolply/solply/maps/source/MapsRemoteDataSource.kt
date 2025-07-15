package com.teamsolply.solply.maps.source

import com.teamsolply.solply.maps.dto.response.GetPlaceDetailResponseDto

interface MapsRemoteDataSource {
    suspend fun getPlaceDetail(placeId: Long): GetPlaceDetailResponseDto
    suspend fun savePlaceBookMark(placeId: Long)
    suspend fun deletePlaceBookMark(placeId: Long)
}
