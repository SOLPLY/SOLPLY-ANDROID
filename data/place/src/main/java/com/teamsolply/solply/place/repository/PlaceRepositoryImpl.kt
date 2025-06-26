package com.teamsolply.solply.place.repository

import com.teamsolply.solply.place.model.PlaceEntity
import com.teamsolply.solply.place.model.SaveAutoSignInEntity
import com.teamsolply.solply.place.source.PlaceLocalDataSource
import com.teamsolply.solply.place.source.PlaceRemoteDataSource
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeLocalDataSource: PlaceLocalDataSource,
    private val placeRemoteDataSource: PlaceRemoteDataSource
) : PlaceRepository {
    override suspend fun saveAutoSignIn(autoSignIn: SaveAutoSignInEntity): Result<Unit> =
        runCatching {
            placeLocalDataSource.saveAutoSignIn(
                autoSignIn = autoSignIn.autoSignIn
            )
        }

    override suspend fun getRecommendedPlace(): Result<PlaceEntity> = runCatching {
        placeRemoteDataSource.getRecommendedPlace()
    }.mapCatching {
        PlaceEntity(it)
    }
}
