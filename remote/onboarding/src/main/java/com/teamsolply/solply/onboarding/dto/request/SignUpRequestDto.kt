package com.teamsolply.solply.onboarding.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("id")
    val id: Int
)
