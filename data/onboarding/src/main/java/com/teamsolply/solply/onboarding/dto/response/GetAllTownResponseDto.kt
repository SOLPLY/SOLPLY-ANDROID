package com.teamsolply.solply.onboarding.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllTownResponseDto(
    @SerialName("selectedTown")
    val selectedTown: TownDto?,
    @SerialName("favoriteTownList")
    val favoriteTownList: List<TownDto>
)

@Serializable
data class TownDto(
    @SerialName("townId")
    val townId: Long,
    @SerialName("townName")
    val townName: String
)
