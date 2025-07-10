package com.teamsolply.solply.place.repository

import com.teamsolply.solply.place.model.RecommendPlaceInfo
import com.teamsolply.solply.place.model.SaveAutoSignInEntity

interface PlaceRepository {
    suspend fun saveAutoSignIn(autoSignIn: SaveAutoSignInEntity): Result<Unit>
    suspend fun getRecommendedPlace(): Result<List<RecommendPlaceInfo>>
}