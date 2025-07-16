package com.teamsolply.solply.place.repository

import com.teamsolply.solply.place.model.RecommendPlaceInfo
import com.teamsolply.solply.place.model.SaveAutoSignInEntity
import com.teamsolply.solply.place.model.TagEntity
import com.teamsolply.solply.place.model.UserInfo

interface PlaceRepository {
    suspend fun saveAutoSignIn(autoSignIn: SaveAutoSignInEntity): Result<Unit>
    suspend fun getRecommendedPlace(townId: Long): Result<List<RecommendPlaceInfo>>
    suspend fun getTags(parentId: Int?): Result<List<TagEntity>>
    suspend fun getUserInfo(): Result<UserInfo>
}
