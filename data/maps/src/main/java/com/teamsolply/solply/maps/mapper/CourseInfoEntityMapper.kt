package com.teamsolply.solply.maps.mapper

import com.teamsolply.solply.maps.dto.response.CourseDto
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.model.PlaceType

fun CourseDto.toEntity(): CourseInfoEntity {
    return CourseInfoEntity(
        courseId = this.courseId.toInt(),
        title = this.title,
        placeCount = this.placeCount,
        thumbnailImage = this.thumbnailImage,
        mainTag = this.mainTags.map { PlaceType.valueOf(it) },
        isBookmarked = this.isBookmarked,
        isActive = this.isActive
    )
}