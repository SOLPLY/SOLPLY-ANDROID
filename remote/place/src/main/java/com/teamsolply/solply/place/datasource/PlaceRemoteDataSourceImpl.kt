package com.teamsolply.solply.place.datasource

import com.teamsolply.solply.place.service.PlaceService
import com.teamsolply.solply.place.source.PlaceRemoteDataSource
import javax.inject.Inject

class PlaceRemoteDataSourceImpl @Inject constructor(
    private val placeService: PlaceService
) : PlaceRemoteDataSource {
    override suspend fun getRecommendedPlace(): String =
        placeService.getRecommendedPlace().data.placeName
}
