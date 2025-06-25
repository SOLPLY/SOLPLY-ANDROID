package com.teamsolply.solply.oauth.repository

import com.teamsolply.solply.datastore.SolplyTokenData
import com.teamsolply.solply.oauth.model.TokenEntity
import com.teamsolply.solply.oauth.source.OauthLocalDataSource
import javax.inject.Inject

class OauthRepositoryImpl @Inject constructor(
    private val oauthLocalDataSource: OauthLocalDataSource
) : OauthRepository {
    override suspend fun saveJwtToken(jwtToken: TokenEntity): Result<Unit> = runCatching {
        oauthLocalDataSource.setAuthLocalData(
            jwtToken = SolplyTokenData(
                accessToken = jwtToken.accessToken,
                refreshToken = jwtToken.refreshToken
            )
        )
    }
}
