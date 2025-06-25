package com.teamsolply.solply.place.source

import com.teamsolply.solply.datastore.SolplyTokenData

interface PlaceLocalDataSource {
    suspend fun saveAutoSignIn(autoSignIn: SolplyTokenData)
}
