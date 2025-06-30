package com.teamsolply.solply.network.service

import com.teamsolply.solply.network.model.ResponsePostAuthRefreshDto
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenRefreshService {
    @POST("")
    suspend fun postRefreshJwtToken(
        @Header("Authorization") refreshToken: String
    ): ResponsePostAuthRefreshDto
}
