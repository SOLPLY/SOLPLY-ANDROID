package com.teamsolply.solply.collection.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponseDto(
    @SerialName("nickname")
    val nickname: String
)
