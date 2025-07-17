package com.teamsolply.solply.course.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponseDto(
    @SerialName("userId")
    val userId: Long,

    @SerialName("nickname")
    val nickname: String,

    @SerialName("selectedTown")
    val selectedTown: TownDto,

    @SerialName("persona")
    val persona: String
)

@Serializable
data class TownDto(
    @SerialName("townId")
    val townId: Long,

    @SerialName("townName")
    val townName: String
)
