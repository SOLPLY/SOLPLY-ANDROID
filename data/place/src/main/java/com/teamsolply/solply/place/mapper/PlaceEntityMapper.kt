package com.teamsolply.solply.place.mapper

import com.teamsolply.solply.place.dto.response.PlaceDto
import com.teamsolply.solply.place.model.PlaceEntity

fun PlaceDto.toEntity(): PlaceEntity {
    return PlaceEntity(
        placeId = this.placeId,
        placeName = this.placeName,
        thumbnailImageUrl = this.thumbnailImageUrl,
        primaryTag = this.mainTag,
        isBookmarked = this.isBookmarked
    )
}
