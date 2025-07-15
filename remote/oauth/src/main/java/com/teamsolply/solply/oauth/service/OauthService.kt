package com.teamsolply.solply.oauth.service

import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.oauth.dto.response.SocialLoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface OauthService {
    @POST("api/auth/social/{provider}/login")
    suspend fun postSocialLogin(
        @Path("provider") provider: String,
        @Body oauthAccessToken: String
    ): BaseResponse<SocialLoginResponseDto>
}
