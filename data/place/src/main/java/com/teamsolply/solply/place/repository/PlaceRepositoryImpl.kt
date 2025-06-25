package com.teamsolply.solply.place.repository

import com.teamsolply.solply.datastore.SolplyTokenData
import com.teamsolply.solply.place.model.SaveAutoSignInEntity
import com.teamsolply.solply.place.source.PlaceLocalDataSource
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeLocalDataSource: PlaceLocalDataSource
) : PlaceRepository {
    override suspend fun saveAutoSignIn(autoSignIn: SaveAutoSignInEntity): Result<Unit> = runCatching {
        placeLocalDataSource.saveAutoSignIn(
            autoSignIn = SolplyTokenData(
                autoSignIn = autoSignIn.autoSignIn
            )
        )
    }
}