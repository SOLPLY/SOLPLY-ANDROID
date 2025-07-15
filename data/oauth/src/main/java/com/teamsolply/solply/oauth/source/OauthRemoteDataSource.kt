package com.teamsolply.solply.oauth.source

import com.teamsolply.solply.oauth.dto.response.SocialLoginResponseDto

interface OauthRemoteDataSource {
    suspend fun socialLogin(provider: String, oauthAccessToken: String): SocialLoginResponseDto
}
