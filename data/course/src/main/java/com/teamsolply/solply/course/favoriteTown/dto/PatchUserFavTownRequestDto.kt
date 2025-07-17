package com.teamsolply.solply.course.favoriteTown.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchUserFavTownRequestDto(
    @SerialName("selectedTownId")
    val selectedTownId: Long,
    @SerialName("favoriteTownIdList")
    val favoriteTownIdList: List<Long>
)
