package com.teamsolply.solply.onboarding.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchUserInfoRequestDto(
    @SerialName("selectedTownId")
    val selectedTownId: Long,

    @SerialName("favoriteTownIdList")
    val favoriteTownIdList: List<Long>,

    @SerialName("persona")
    val persona: String,

    @SerialName("nickname")
    val nickname: String
)
