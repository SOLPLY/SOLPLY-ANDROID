package com.teamsolply.solply.onboarding.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NicknameDuplicateResponseDto(
    @SerialName("isDuplicated")
    val isDuplicated: Boolean
)
