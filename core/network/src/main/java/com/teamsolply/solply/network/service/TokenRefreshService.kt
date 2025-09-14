package com.teamsolply.solply.network.service

import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.network.model.ResponsePostAuthRefreshDto
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenRefreshService {
    @POST("api/auth/refresh")
    suspend fun postRefreshJwtToken(
        @Header("Refresh-Token") refreshToken: String
    ): BaseResponse<ResponsePostAuthRefreshDto>
}
