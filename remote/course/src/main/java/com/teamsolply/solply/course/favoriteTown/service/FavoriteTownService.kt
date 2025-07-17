package com.teamsolply.solply.course.favoriteTown.service

import com.teamsolply.solply.course.favoriteTown.dto.FavoriteTownUserInfoResponseDto
import com.teamsolply.solply.course.favoriteTown.dto.PatchUserFavTownRequestDto
import com.teamsolply.solply.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface FavoriteTownService {
    @GET("/api/users/towns")
    suspend fun getTownList(): BaseResponse<FavoriteTownUserInfoResponseDto>

    @PATCH("/api/users/towns")
    suspend fun patchUserFavoriteTown(
        @Body body: PatchUserFavTownRequestDto
    ): BaseResponse<Unit>
}
