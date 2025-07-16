package com.teamsolply.solply.maps.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDetailResponseDto(
    @SerialName("courseId")
    val courseId: Long,

    @SerialName("courseName")
    val courseName: String,

    @SerialName("introduction")
    val introduction: String,

    @SerialName("isBookmarked")
    val isBookmarked: Boolean,

    @SerialName("places")
    val places: List<CoursePlaceDto>
)

@Serializable
data class CoursePlaceDto(
    @SerialName("placeId")
    val placeId: Long,

    @SerialName("placeName")
    val placeName: String,

    @SerialName("thumbnailUrl")
    val thumbnailUrl: String?,

    @SerialName("primaryTag")
    val primaryTag: String,

    @SerialName("address")
    val address: String,

    @SerialName("isBookmarked")
    val isBookmarked: Boolean,

    @SerialName("placeOrder")
    val placeOrder: Int,

    @SerialName("latitude")
    val latitude: String,

    @SerialName("longitude")
    val longitude: String,

    @SerialName("placeType")
    val placeType: String,

    @SerialName("placeDefaultId")
    val placeDefaultId: Long
)
