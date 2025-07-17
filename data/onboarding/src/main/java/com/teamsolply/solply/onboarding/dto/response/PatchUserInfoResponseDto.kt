package com.teamsolply.solply.onboarding.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchUserInfoResponseDto(
    @SerialName("selectedTownId")
    val selectedTownId: Long,
    @SerialName("selectedTownName")
    val selectedTownName: String,
    @SerialName("persona")
    val persona: String,
    @SerialName("nickname")
    val nickname: String
)
