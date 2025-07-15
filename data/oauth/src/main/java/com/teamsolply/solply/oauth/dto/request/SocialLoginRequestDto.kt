package com.teamsolply.solply.oauth.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginRequestDto(
    @SerialName("oauthAccessToken")
    val oauthAccessToken: String
)
