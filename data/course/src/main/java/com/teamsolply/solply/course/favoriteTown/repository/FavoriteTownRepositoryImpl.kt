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
        val tree = remoteDataSource.getTownTree()

        val regions = tree.towns.map { region ->
            Region(
                id = region.townId,
                name = region.townName ?: region.name.orEmpty()
            )
        }

        val townsByRegion: Map<Long, List<TownLite>> = tree.towns.associate { region ->
            val towns = region.subTowns.orEmpty().map { sub ->
                TownLite(
                    id = sub.townId,
                    name = sub.townName ?: sub.name.orEmpty()
                )
            }
            region.townId to towns
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
