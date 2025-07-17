package com.teamsolply.solply.maps.model

data class CourseDetailEntity(
    val courseId: Long,
    val courseName: String,
    val introduction: String,
    val isBookmarked: Boolean,
    val places: List<Place>
)

data class Place(
    val placeId: Long,
    val placeName: String,
    val thumbnailUrl: String,
    val primaryTag: String,
    val address: String,
    val isBookmarked: Boolean,
    val placeOrder: Int,
    val latitude: Double,
    val longitude: Double,
    val placeTag: String,
    val placeDefaultId: Int
)
