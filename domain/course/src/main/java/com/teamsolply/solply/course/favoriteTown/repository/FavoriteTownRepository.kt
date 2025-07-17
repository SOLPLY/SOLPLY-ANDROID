package com.teamsolply.solply.course.favoriteTown.repository

import com.teamsolply.solply.course.favoriteTown.model.FavoriteTownInfoEntity

interface FavoriteTownRepository {
    suspend fun getTownList(): Result<FavoriteTownInfoEntity>
    suspend fun patchUserFavoriteTown(
        selectedTownId: Int,
        favoriteTownIdList: List<Int>
    ): Result<Unit>
}