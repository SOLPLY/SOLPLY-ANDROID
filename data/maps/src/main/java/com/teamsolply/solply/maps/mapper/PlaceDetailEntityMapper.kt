import com.teamsolply.solply.maps.dto.response.GetPlaceDetailResponseDto
import com.teamsolply.solply.maps.dto.response.ImageInfoDto
import com.teamsolply.solply.maps.dto.response.SnsLinkDto
import com.teamsolply.solply.maps.model.ImageInfo
import com.teamsolply.solply.maps.model.PlaceDetailEntity
import com.teamsolply.solply.maps.model.SnsLink
import com.teamsolply.solply.model.PlaceType

fun GetPlaceDetailResponseDto.toEntity(): PlaceDetailEntity {
    return PlaceDetailEntity(
        placeId = placeId,
        placeName = placeName,
        mainTag = PlaceType.valueOf(mainTag),
        introduction = introduction,
        imageInfos = imageInfoDtos.map { it.toEntity() },
        address = address,
        latitude = latitude.toDouble(),
        longitude = longitude.toDouble(),
        contactNumber = contactNumber ?: "",
        openingHours = openingHours,
        snsLinks = snsLinkDtos.map { it.toEntity() },
        isBookmarked = isBookmarked,
        placeType = placeType,
        placeDefaultId = placeDefaultId
    )
}

fun ImageInfoDto.toEntity(): ImageInfo {
    return ImageInfo(
        displayOrder = displayOrder,
        url = url
    )
}

fun SnsLinkDto.toEntity(): SnsLink {
    return SnsLink(
        snsPlatform = snsPlatform,
        url = url
    )
}
