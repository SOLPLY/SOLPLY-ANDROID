package com.teamsolply.solply.place.repository

import com.teamsolply.solply.place.source.PlaceLocalDataSource
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeLocalDataSource: PlaceLocalDataSource
) : PlaceRepository {
    override suspend fun getAutoSignIn(): Result<Boolean> = runCatching {
        placeLocalDataSource.getAutoSignIn()
    }
}