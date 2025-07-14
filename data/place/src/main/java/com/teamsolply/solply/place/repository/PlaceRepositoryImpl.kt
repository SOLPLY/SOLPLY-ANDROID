package com.teamsolply.solply.place.repository

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.place.model.RecommendPlaceInfo
import com.teamsolply.solply.place.model.SaveAutoSignInEntity
import com.teamsolply.solply.place.model.TagEntity
import com.teamsolply.solply.place.source.PlaceLocalDataSource
import com.teamsolply.solply.place.source.PlaceRemoteDataSource
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeLocalDataSource: PlaceLocalDataSource,
    private val placeRemoteDataSource: PlaceRemoteDataSource
) : PlaceRepository {

    override suspend fun saveAutoSignIn(autoSignIn: SaveAutoSignInEntity): Result<Unit> =
        runCatching {
            placeLocalDataSource.saveAutoSignIn(autoSignIn.autoSignIn)
        }

    override suspend fun getRecommendedPlace(): Result<List<RecommendPlaceInfo>> = runCatching {
        listOf(
            RecommendPlaceInfo(
                placeId = 0,
                placeName = "장소 이름",
                thumbnailImageUrl = 0,
                primaryTag = PlaceType.CAFE,
                description = "장소 한 줄 소개 장소 한 줄 소개"
            ),
            RecommendPlaceInfo(
                placeId = 1,
                placeName = "장소 이름",
                thumbnailImageUrl = 1,
                primaryTag = PlaceType.FOOD,
                description = "장소 한 줄 소개 장소 한 줄 소개"
            ),
            RecommendPlaceInfo(
                placeId = 2,
                placeName = "장소 이름",
                thumbnailImageUrl = 2,
                primaryTag = PlaceType.UNIQUE,
                description = "장소 한 줄 소개 장소 한 줄 소개 두 줄이 되어도 괜찮음음음음음음"
            )
        )
    }

    override suspend fun getMainTag(): Result<List<TagEntity>> = runCatching {
        placeRemoteDataSource.getTags()
    }.mapCatching { tagDto ->
        tagDto.map {
            TagEntity(
                tagId = it.tagId,
                tagType = it.tagType,
                name = it.name,
                parentId = it.parentId
            )
        }
    }
}
