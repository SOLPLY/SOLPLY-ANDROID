package com.teamsolply.solply.place.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserInfoResponseDto(
    @SerialName("userId")
    val userId: Long,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("selectedTown")
    val selectedTown: SelectedTownDto,
    @SerialName("persona")
    val persona: String
)

@Serializable
data class SelectedTownDto(
    @SerialName("townId")
    val townId: Long,
    @SerialName("townName")
    val townName: String
)