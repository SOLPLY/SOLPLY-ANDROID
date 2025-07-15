package com.teamsolply.solply.oauth.datasource

import com.teamsolply.solply.oauth.dto.response.SocialLoginResponseDto
import com.teamsolply.solply.oauth.service.OauthService
import com.teamsolply.solply.oauth.source.OauthRemoteDataSource
import javax.inject.Inject

class OauthRemoteDataSourceImpl @Inject constructor(
    private val oauthService: OauthService
) : OauthRemoteDataSource {
    override suspend fun socialLogin(
        provider: String,
        oauthAccessToken: String
    ): SocialLoginResponseDto = oauthService.postSocialLogin(
        provider = provider,
        oauthAccessToken = oauthAccessToken
    ).data
}
