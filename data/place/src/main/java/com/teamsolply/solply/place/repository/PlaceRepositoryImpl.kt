package com.teamsolply.solply.place.repository

import android.util.Log
import com.teamsolply.solply.model.PlaceType
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
        Log.d("tagList", tagDtoList.toString())
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
        Log.d("tagList", tagEntityList.toString())
        tagEntityList
    }

    override suspend fun getSubTags(parentId: Int): Result<List<TagEntity>> = runCatching {
        placeRemoteDataSource.getTags(parentId)
    }.mapCatching { tagDtoList ->
        Log.d("tagList", tagDtoList.toString())
        val tagEntityList = tagDtoList.map {
            TagEntity(
                tagId = it.tagId,
                tagType = it.tagType,
                name = it.name,
                parentId = it.parentId
            )
        }
        Log.d("tagList", tagEntityList.toString())
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
                    primaryTag = PlaceType.fromApiName(it.primaryTag),
                    introduction = it.introduction
                )
            }
        }
}
