package com.teamsolply.solply.maps.mapper

import com.teamsolply.solply.maps.dto.request.CoursePlaceDto
import com.teamsolply.solply.maps.dto.request.CourseSaveRequestDto
import com.teamsolply.solply.maps.model.CoursePlace
import com.teamsolply.solply.maps.model.CourseSaveEntity

fun CourseSaveEntity.toDto(): CourseSaveRequestDto {
    return CourseSaveRequestDto(
        courseName = name,
        courseDescription = description,
        places = places.map { it.toDto() }
    )
}

fun CoursePlace.toDto(): CoursePlaceDto {
    return CoursePlaceDto(
        placeId = placeId,
        placeOrder = order
    )
}