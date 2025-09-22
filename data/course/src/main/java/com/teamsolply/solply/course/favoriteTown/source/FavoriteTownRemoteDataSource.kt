package com.teamsolply.solply.course.favoriteTown.source

import com.teamsolply.solply.course.favoriteTown.dto.PatchUserFavTownRequestDto
import com.teamsolply.solply.course.favoriteTown.dto.TownTreeResponseDto

interface FavoriteTownRemoteDataSource {
    suspend fun getTownTree(): TownTreeResponseDto
    suspend fun patchUserFavoriteTown(
        patchUserFavoriteTownDto: PatchUserFavTownRequestDto
    )
}
