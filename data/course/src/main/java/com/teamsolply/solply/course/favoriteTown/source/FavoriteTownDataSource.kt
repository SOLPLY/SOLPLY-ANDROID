package com.teamsolply.solply.course.favoriteTown.source

import com.teamsolply.solply.course.favoriteTown.dto.FavoriteTownUserInfoResponseDto

interface FavoriteTownRemoteDataSource {
    suspend fun getTownList(): FavoriteTownUserInfoResponseDto
}