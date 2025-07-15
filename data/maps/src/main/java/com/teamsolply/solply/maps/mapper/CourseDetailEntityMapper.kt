package com.teamsolply.solply.maps.mapper

import com.teamsolply.solply.maps.dto.response.CourseDetailResponseDto
import com.teamsolply.solply.maps.dto.response.CoursePlaceDto
import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.Place

fun CourseDetailResponseDto.toEntity(): CourseDetailEntity {
    return CourseDetailEntity(
        courseId = courseId,
        courseName = courseName,
        introduction = introduction,
        isBookmarked = isBookmarked,
        places = places.map { it.toEntity() }
    )
}

fun CoursePlaceDto.toEntity(): Place {
    return Place(
        placeId = placeId,
        placeName = placeName,
        thumbnailUrl = thumbnailUrl,
        primaryTag = primaryTag,
        address = address,
        isBookmarked = isBookmarked,
        placeOrder = placeOrder,
        latitude = latitude,
        longitude = longitude,
        placeTag = placeType,
        placeDefaultId = placeDefaultId.toInt()
    )
}