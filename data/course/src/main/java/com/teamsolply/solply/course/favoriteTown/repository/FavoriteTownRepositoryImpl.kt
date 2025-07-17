package com.teamsolply.solply.course.favoriteTown.repository

import com.teamsolply.solply.course.favoriteTown.dto.PatchUserFavTownRequestDto
import com.teamsolply.solply.course.favoriteTown.model.FavoriteTownInfoEntity
import com.teamsolply.solply.course.favoriteTown.model.Town
import com.teamsolply.solply.course.favoriteTown.source.FavoriteTownRemoteDataSource
import javax.inject.Inject

class FavoriteTownRepositoryImpl @Inject constructor(
    private val remoteDataSource: FavoriteTownRemoteDataSource
) : FavoriteTownRepository {

    override suspend fun getTownList(): Result<FavoriteTownInfoEntity> = runCatching {
        val dto = remoteDataSource.getTownList()

        val selectedTown = Town(
            townId = dto.selectedTown.townId,
            townName = dto.selectedTown.townName
        )

        val favoriteTownList = dto.favoriteTownList.map {
            Town(
                townId = it.townId,
                townName = it.townName
            )
        }

        FavoriteTownInfoEntity(
            selectedTown = selectedTown,
            favoriteTownList = favoriteTownList
        )
    }

    override suspend fun patchUserFavoriteTown(
        selectedTownId: Int,
        favoriteTownList: List<Int>
    ): Result<Unit> {
        return runCatching {
            val request = PatchUserFavTownRequestDto(
                selectedTownId = selectedTownId,
                favoriteTownIdList = favoriteTownList
            )
            remoteDataSource.patchUserFavoriteTown(request)
        }
    }
}
