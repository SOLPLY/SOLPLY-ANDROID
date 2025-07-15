package com.teamsolply.solply.oauth.service

import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.oauth.dto.request.SocialLoginRequestDto
import com.teamsolply.solply.oauth.dto.response.SocialLoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface OauthService {
    @POST("api/auth/social/{soicialPlatform}/login")
    suspend fun postSocialLogin(
        @Path("soicialPlatform") soicialPlatform: String,
        @Body oauthAccessToken: SocialLoginRequestDto
    ): BaseResponse<SocialLoginResponseDto>
}
