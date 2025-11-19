package com.teamsolply.solply.course.favoriteTown.repository

import com.teamsolply.solply.course.favoriteTown.dto.PatchUserFavTownRequestDto
import com.teamsolply.solply.course.favoriteTown.model.Region
import com.teamsolply.solply.course.favoriteTown.model.TownLite
import com.teamsolply.solply.course.favoriteTown.source.FavoriteTownRemoteDataSource
import javax.inject.Inject

class FavoriteTownRepositoryImpl @Inject constructor(
    private val remoteDataSource: FavoriteTownRemoteDataSource
) : FavoriteTownRepository {

    override suspend fun getTownTree():
            Result<Pair<List<Region>, Map<Long, List<TownLite>>>> = runCatching {
        val towns = remoteDataSource.getTownTree().towns

        val regions = towns
            .filter { it.parentTownId == null || it.parentTownId == 0L }
            .map { region ->
                Region(
                    id = region.townId,
                    name = region.townName
                )
            }

        val townsByRegion: Map<Long, List<TownLite>> = towns
            .filter { it.parentTownId != null && it.parentTownId != 0L }
            .groupBy { it.parentTownId ?: 0L }
            .mapValues { (_, children) ->
                children.map { child ->
                    TownLite(
                        id = child.townId,
                        name = child.townName
                    )
                }
            }

        regions to townsByRegion
    }

    override suspend fun patchUserFavoriteTown(
        selectedTownId: Long,
        favoriteTownList: List<Long>
    ): Result<Unit> = runCatching {
        val req = PatchUserFavTownRequestDto(
            selectedTownId = selectedTownId,
            favoriteTownIdList = favoriteTownList
        )
        remoteDataSource.patchUserFavoriteTown(req)
    }
}