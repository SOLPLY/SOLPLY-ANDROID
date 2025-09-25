package com.teamsolply.solply.search.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("places")
    val places: List<SearchResult>
)

@Serializable
data class SearchResult(
    @SerialName("placeId")
    val placeId: Long,
    @SerialName("placeName")
    val placeName: String,
    @SerialName("thumbnailImageUrl")
    val thumbnailImageUrl: String,
    @SerialName("primaryTag")
    val primaryTag: String,
    @SerialName("address")
    val address: String,
    @SerialName("isBookmarked")
    val isBookmarked: Boolean
)
