package com.teamsolply.solply.place.repository

import com.teamsolply.solply.place.model.PlaceEntity
import com.teamsolply.solply.place.model.RecommendPlaceInfo
import com.teamsolply.solply.place.model.SaveAutoSignInEntity
import com.teamsolply.solply.place.model.TagEntity
import com.teamsolply.solply.place.model.UserInfo

interface PlaceRepository {
    suspend fun saveAutoSignIn(autoSignIn: SaveAutoSignInEntity): Result<Unit>
    suspend fun getRecommendedPlace(townId: Long): Result<List<RecommendPlaceInfo>>
    suspend fun getMainTags(): Result<List<TagEntity>>
    suspend fun getSubTags(parentId: Int): Result<List<TagEntity>>
    suspend fun getUserInfo(): Result<UserInfo>
    suspend fun getPlaces(
        townId: Long,
        mainTagId: Int?,
        subTagAIdList: List<Int>?,
        subTagBIdList: List<Int>?
    ): Result<List<PlaceEntity>>
}
