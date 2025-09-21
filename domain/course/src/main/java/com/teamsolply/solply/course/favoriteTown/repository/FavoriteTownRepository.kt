package com.teamsolply.solply.course.favoriteTown.repository

import com.teamsolply.solply.course.favoriteTown.model.Region
import com.teamsolply.solply.course.favoriteTown.model.TownLite

interface FavoriteTownRepository {
    suspend fun getTownTree(): Result<Pair<List<Region>, Map<Long, List<TownLite>>>>
    suspend fun patchUserFavoriteTown(
        selectedTownId: Long,
        favoriteTownList: List<Long>
    ): Result<Unit>
}
