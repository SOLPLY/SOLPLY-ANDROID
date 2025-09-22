package com.teamsolply.solply.course.favoriteTown.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TownTreeResponseDto(
    @SerialName("towns") val towns: List<TownNodeDto> = emptyList()
)

@Serializable
data class TownNodeDto(
    @SerialName("townId") val townId: Long,
    @SerialName("townName") val townName: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("subTowns") val subTowns: List<TownNodeDto>? = null
)
