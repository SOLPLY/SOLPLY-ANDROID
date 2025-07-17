package com.teamsolply.solply.place.repository

import android.util.Log
import com.teamsolply.solply.place.model.PlaceEntity
import com.teamsolply.solply.place.model.RecommendPlaceInfo
import com.teamsolply.solply.place.model.SaveAutoSignInEntity
import com.teamsolply.solply.place.model.SelectedTownInfo
import com.teamsolply.solply.place.model.TagEntity
import com.teamsolply.solply.place.model.UserInfo
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

    override suspend fun getMainTags(): Result<List<TagEntity>> = runCatching {
        placeRemoteDataSource.getTags(null)
    }.mapCatching { tagDtoList ->
        val tagEntityList = tagDtoList.map {
            TagEntity(
                tagId = it.tagId,
                tagType = it.tagType,
                name = it.name,
                parentId = it.parentId
            )
        }.toMutableList()
        tagEntityList.add(
            0,
            TagEntity(
                tagId = 0,
                tagType = "MAIN",
                name = "ALL",
                parentId = null
            )
        )
        tagEntityList
    }

    override suspend fun getSubTags(parentId: Int): Result<List<TagEntity>> = runCatching {
        placeRemoteDataSource.getTags(parentId)
    }.mapCatching { tagDtoList ->
        val tagEntityList = tagDtoList.map {
            TagEntity(
                tagId = it.tagId,
                tagType = it.tagType,
                name = it.name,
                parentId = it.parentId
            )
        }
        tagEntityList
    }

    override suspend fun getUserInfo(): Result<UserInfo> = runCatching {
        placeRemoteDataSource.getUserInfo()
    }.mapCatching { userDto ->
        UserInfo(
            userId = userDto.userId,
            nickname = userDto.nickname,
            selectedTown = SelectedTownInfo(
                townId = userDto.selectedTown.townId,
                townName = userDto.selectedTown.townName
            ),
            persona = userDto.persona
        )
    }

    override suspend fun getRecommendedPlace(townId: Long): Result<List<RecommendPlaceInfo>> =
        runCatching {
            placeRemoteDataSource.getRecommendPlace(townId)
        }.mapCatching { recommendDto ->
            recommendDto.map {
                RecommendPlaceInfo(
                    placeId = it.placeId,
                    placeName = it.placeName,
                    thumbnailImageUrl = it.thumbnailImageUrl,
                    primaryTag = it.primaryTag,
                    introduction = it.introduction
                )
            }.take(3)
        }

    override suspend fun getPlaces(
        townId: Long,
        mainTagId: Int?,
        subTagAIdList: List<Int>?,
        subTagBIdList: List<Int>?
    ): Result<List<PlaceEntity>> = runCatching {
        placeRemoteDataSource.getPlaces(
            townId = townId,
            isBookmarkSearch = false,
            mainTagId = mainTagId,
            subTagAIdList = subTagAIdList,
            subTagBIdList = subTagBIdList
        )
    }.mapCatching { responseDto ->
        responseDto.places.map { dto ->
            PlaceEntity(
                placeId = dto.placeId,
                placeName = dto.placeName,
                thumbnailImageUrl = dto.thumbnailImageUrl,
                primaryTag = dto.primaryTag,
                isBookmarked = dto.isBookmarked
            )
        }
    }
}
