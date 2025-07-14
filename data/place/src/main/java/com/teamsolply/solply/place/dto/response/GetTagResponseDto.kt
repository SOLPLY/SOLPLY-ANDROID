package com.teamsolply.solply.place.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTagResponseDto(
    @SerialName("tagId")
    val tagId: Int,
    @SerialName("tagType")
    val tagType: String,
    @SerialName("name")
    val name: String,
    @SerialName("parentId")
    val parentId: Int
)
