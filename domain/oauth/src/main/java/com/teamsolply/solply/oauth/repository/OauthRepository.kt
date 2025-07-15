package com.teamsolply.solply.oauth.repository

import com.teamsolply.solply.oauth.model.TokenEntity

interface OauthRepository {
    suspend fun postSocialLogin(provider: String, oauthAccessToken: String): Result<TokenEntity>
    suspend fun saveJwtToken(accessToken: String, refreshToken: String): Result<Unit>
}
