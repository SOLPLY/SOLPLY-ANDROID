package com.teamsolply.solply.oauth.repository

import com.teamsolply.solply.oauth.model.TokenEntity
import com.teamsolply.solply.oauth.source.OauthLocalDataSource
import com.teamsolply.solply.oauth.source.OauthRemoteDataSource
import javax.inject.Inject

class OauthRepositoryImpl @Inject constructor(
    private val oauthLocalDataSource: OauthLocalDataSource,
    private val oauthRemoteDataSource: OauthRemoteDataSource
) : OauthRepository {

    override suspend fun postSocialLogin(
        provider: String,
        oauthAccessToken: String
    ): Result<TokenEntity> = runCatching {
        oauthRemoteDataSource.socialLogin(
            provider = provider,
            oauthAccessToken = oauthAccessToken
        )
    }.mapCatching {
        TokenEntity(
            accessToken = it.accessToken,
            refreshToken = it.refreshToken,
            isNewUser = it.isNewUser
        )
    }

    override suspend fun saveJwtToken(accessToken: String, refreshToken: String): Result<Unit> =
        runCatching {
            oauthLocalDataSource.setAuthLocalData(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
}
