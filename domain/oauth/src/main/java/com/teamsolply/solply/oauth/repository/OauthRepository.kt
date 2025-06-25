package com.teamsolply.solply.oauth.repository

import com.teamsolply.solply.oauth.model.TokenEntity

interface OauthRepository {
    suspend fun saveJwtToken(jwtToken: TokenEntity): Result<Unit>
}
