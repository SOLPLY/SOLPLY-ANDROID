package com.teamsolply.solply.place.datasource

import androidx.datastore.core.DataStore
import com.teamsolply.solply.datastore.SolplyTokenData
import com.teamsolply.solply.place.source.PlaceLocalDataSource
import javax.inject.Inject

class PlaceLocalDataSourceImpl @Inject constructor(
    private val placeLocalDataSource: DataStore<SolplyTokenData>
) : PlaceLocalDataSource {
    override suspend fun saveAutoSignIn(autoSignIn: SolplyTokenData) {
        placeLocalDataSource.updateData { old ->
            old.copy(
                autoSignIn = autoSignIn.autoSignIn
            )
        }
    }
}
