package com.teamsolply.solply.course.favoriteTown.source

import com.teamsolply.solply.course.favoriteTown.dto.FavoriteTownUserInfoResponseDto
import com.teamsolply.solply.course.favoriteTown.dto.PatchUserFavTownRequestDto

interface FavoriteTownRemoteDataSource {
    suspend fun getTownList(): FavoriteTownUserInfoResponseDto
    suspend fun patchUserFavoriteTown(
        patchUserFavoriteTownDto: PatchUserFavTownRequestDto
    )
}