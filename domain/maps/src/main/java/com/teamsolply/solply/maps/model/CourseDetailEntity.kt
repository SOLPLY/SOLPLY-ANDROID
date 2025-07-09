package com.teamsolply.solply.maps.model

data class CourseDetailEntity(
    val courseId: Int,
    val courseName: String,
    val introduction: String,
    val places: List<Place>
)

data class Place(
    val placeId: Int,
    val placeName: String,
    val thumbnailUrl: String,
    val primaryTag: String,
    val address: String,
    val isBookmarked: Boolean,
    val placeOrder: Int,
    val latitude: String,
    val longitude: String,
    val placeTag: String,
    val placeDefaultId: Int
)