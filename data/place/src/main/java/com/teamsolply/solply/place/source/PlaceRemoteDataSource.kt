package com.teamsolply.solply.place.source

interface PlaceRemoteDataSource {
    suspend fun getRecommendedPlace(): String
}
