package com.teamsolply.solply.onboarding.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchUserInfoRequestDto(
    @SerialName("favoriteTown")
    val favoriteTown: Long,
    @SerialName("persona")
    val persona: String,
    @SerialName("nickname")
    val nickname: String
)
