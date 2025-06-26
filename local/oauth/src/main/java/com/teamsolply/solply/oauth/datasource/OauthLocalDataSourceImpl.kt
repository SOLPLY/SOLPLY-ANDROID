package com.teamsolply.solply.oauth.datasource

import androidx.datastore.core.DataStore
import com.teamsolply.solply.datastore.SolplyTokenData
import com.teamsolply.solply.oauth.source.OauthLocalDataSource
import javax.inject.Inject

class OauthLocalDataSourceImpl @Inject constructor(
    private val oauthLocalDataSource: DataStore<SolplyTokenData>
) : OauthLocalDataSource {
    override suspend fun setAuthLocalData(accessToken: String, refreshToken: String) {
        oauthLocalDataSource.updateData { old ->
            old.copy(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }
}
