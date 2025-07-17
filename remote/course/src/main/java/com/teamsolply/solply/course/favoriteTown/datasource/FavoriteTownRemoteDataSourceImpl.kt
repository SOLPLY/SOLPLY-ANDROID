package com.teamsolply.solply.course.favoriteTown.datasource

import com.teamsolply.solply.course.favoriteTown.dto.FavoriteTownUserInfoResponseDto
import com.teamsolply.solply.course.favoriteTown.dto.PatchUserFavTownRequestDto
import com.teamsolply.solply.course.favoriteTown.service.FavoriteTownService
import com.teamsolply.solply.course.favoriteTown.source.FavoriteTownRemoteDataSource
import javax.inject.Inject

class FavoriteTownRemoteDataSourceImpl @Inject constructor(
    private val service: FavoriteTownService
) : FavoriteTownRemoteDataSource {

    override suspend fun getTownList(): FavoriteTownUserInfoResponseDto {
        return service.getTownList().data
    }

    override suspend fun patchUserFavoriteTown(
        patchUserFavoriteTownDto: PatchUserFavTownRequestDto
    ) {
        service.patchUserFavoriteTown(patchUserFavoriteTownDto)
    }
}