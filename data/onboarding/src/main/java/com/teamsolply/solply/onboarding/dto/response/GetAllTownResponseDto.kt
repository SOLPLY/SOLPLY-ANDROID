package com.teamsolply.solply.onboarding.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllTownResponseDto(
    @SerialName("towns")
    val mainTownDto: List<MainTownDto>
)

@Serializable
data class MainTownDto(
    @SerialName("townId")
    val townId: Long,
    @SerialName("townName")
    val townName: String,
    @SerialName("subTowns")
    val subTowns: List<SubTownDto>? = null
)

@Serializable
data class SubTownDto(
    val townId: Long,
    val townName: String,
    val subTowns: List<SubTownDto>? = null
)
