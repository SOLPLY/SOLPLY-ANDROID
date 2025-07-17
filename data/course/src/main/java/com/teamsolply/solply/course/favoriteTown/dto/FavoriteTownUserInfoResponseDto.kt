package com.teamsolply.solply.course.favoriteTown.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteTownUserInfoResponseDto(
    @SerialName("selectedTown")
    val selectedTown: SelectedTownDto,
    @SerialName("favoriteTownList")
    val favoriteTownList: List<GetFavoriteTownResponseDto>
)

@Serializable
data class SelectedTownDto(
    @SerialName("townId")
    val townId: Int,
    @SerialName("townName")
    val townName: String
)

@Serializable
data class GetFavoriteTownResponseDto(
    @SerialName("townId")
    val townId: Int,
    @SerialName("townName")
    val townName: String
)