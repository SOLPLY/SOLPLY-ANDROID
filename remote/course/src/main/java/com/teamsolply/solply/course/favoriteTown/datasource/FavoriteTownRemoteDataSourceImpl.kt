package com.teamsolply.solply.course.favoriteTown.datasource

import com.teamsolply.solply.course.favoriteTown.dto.PatchUserFavTownRequestDto
import com.teamsolply.solply.course.favoriteTown.dto.TownTreeResponseDto
import com.teamsolply.solply.course.favoriteTown.service.FavoriteTownService
import com.teamsolply.solply.course.favoriteTown.source.FavoriteTownRemoteDataSource
import javax.inject.Inject

class FavoriteTownRemoteDataSourceImpl @Inject constructor(
    private val service: FavoriteTownService
) : FavoriteTownRemoteDataSource {

    override suspend fun getTownTree(): TownTreeResponseDto {
        return service.getTownTree().data
    }

    override suspend fun patchUserFavoriteTown(
        patchUserFavoriteTownDto: PatchUserFavTownRequestDto
    ) {
        service.patchUserFavoriteTown(patchUserFavoriteTownDto)
    }
}
