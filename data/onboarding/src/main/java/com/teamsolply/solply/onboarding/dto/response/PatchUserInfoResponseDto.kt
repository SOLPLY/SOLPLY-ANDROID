package com.teamsolply.solply.onboarding.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchUserInfoResponseDto(
    @SerialName("favoriteTownId")
    val favoriteTownId: Long,
    @SerialName("favoriteTownName")
    val favoriteTownName: String,
    @SerialName("persona")
    val persona: String,
    @SerialName("nickname")
    val nickname: String
)
