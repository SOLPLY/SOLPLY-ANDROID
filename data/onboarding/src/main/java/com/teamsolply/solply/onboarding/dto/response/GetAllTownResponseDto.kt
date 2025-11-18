package com.teamsolply.solply.onboarding.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllTownResponseDto(
    @SerialName("towns")
    val towns: List<TownDto>
)

@Serializable
data class TownDto(
    @SerialName("townId")
    val townId: Long,
    @SerialName("townName")
    val townName: String,
    @SerialName("parentTownId")
    val parentTownId: Long? = null,
    @SerialName("subTowns")
    val subTowns: List<TownDto>? = null
)