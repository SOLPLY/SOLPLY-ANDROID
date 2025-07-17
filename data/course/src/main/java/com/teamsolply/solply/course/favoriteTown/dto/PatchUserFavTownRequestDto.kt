package com.teamsolply.solply.course.favoriteTown.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchUserFavTownRequestDto(
    @SerialName("selectedTownId")
    val selectedTownId: Int,
    @SerialName("favoriteTownIdList")
    val favoriteTownIdList: List<Int>
)