package com.teamsolply.solply.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostAuthRefreshDto(
    val accessToken: String,
    val refreshToken: String
)
