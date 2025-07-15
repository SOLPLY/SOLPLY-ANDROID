package com.teamsolply.solply.maps.datasource

import com.teamsolply.solply.maps.dto.response.GetPlaceDetailResponseDto
import com.teamsolply.solply.maps.service.MapsService
import com.teamsolply.solply.maps.source.MapsRemoteDataSource
import javax.inject.Inject

class MapsRemoteDataSourceImpl @Inject constructor(
    private val mapsService: MapsService
) : MapsRemoteDataSource {

    override suspend fun getPlaceDetail(placeId: Long): GetPlaceDetailResponseDto =
        mapsService.getPlaceDetail(placeId = placeId).data

    override suspend fun savePlaceBookMark(placeId: Long): Unit =
        mapsService.postPlaceBookMark(placeId = placeId).data


    override suspend fun deletePlaceBookMark(placeId: Long): Unit =
        mapsService.deletePlaceBookMark(placeId = placeId).data
}

