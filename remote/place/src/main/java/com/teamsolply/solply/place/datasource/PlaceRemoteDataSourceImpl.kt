package com.teamsolply.solply.place.datasource

import com.teamsolply.solply.place.dto.response.GetPlacesResponseDto
import com.teamsolply.solply.place.dto.response.GetRecommendPlaceDto
import com.teamsolply.solply.place.dto.response.GetTagResponseDto
import com.teamsolply.solply.place.dto.response.GetUserInfoResponseDto
import com.teamsolply.solply.place.service.PlaceService
import com.teamsolply.solply.place.source.PlaceRemoteDataSource
import javax.inject.Inject

class PlaceRemoteDataSourceImpl @Inject constructor(
    private val placeService: PlaceService
) : PlaceRemoteDataSource {
    override suspend fun getTags(parentId: Int?): List<GetTagResponseDto> {
        return placeService.getTags(parentId).data.tags
    }

    override suspend fun getUserInfo(): GetUserInfoResponseDto {
        return placeService.getUserInfo().data
    }

    override suspend fun getRecommendPlace(townId: Long): List<GetRecommendPlaceDto> {
        return placeService.getRecommendPlace(townId).data.placeInfos
    }

    override suspend fun getPlaces(
        townId: Long,
        isBookmarkSearch: Boolean,
        mainTagId: Int?,
        subTagAIdList: List<Int>?,
        subTagBIdList: List<Int>?
    ): GetPlacesResponseDto {
        return placeService.getPlaces(
            townId = townId,
            isBookmarkSearch = isBookmarkSearch,
            mainTagId = mainTagId,
            subTag1Ids = if (subTagAIdList.isNullOrEmpty()) {
                null
            } else {
                subTagAIdList.joinToString(
                    separator = ","
                )
            },
            subTag2Ids = if (subTagBIdList.isNullOrEmpty()) {
                null
            } else {
                subTagBIdList.joinToString(
                    separator = ","
                )
            }
        ).data
    }
}
