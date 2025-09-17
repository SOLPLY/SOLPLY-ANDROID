package com.teamsolply.solply.course.favoriteTown.service

import com.teamsolply.solply.course.favoriteTown.dto.PatchUserFavTownRequestDto
import com.teamsolply.solply.course.favoriteTown.dto.TownTreeResponseDto
import com.teamsolply.solply.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface FavoriteTownService {
    @GET("/api/towns")
    suspend fun getTownTree(): BaseResponse<TownTreeResponseDto>
    @PATCH("/api/users/towns")
    suspend fun patchUserFavoriteTown(
        @Body body: PatchUserFavTownRequestDto
    ): BaseResponse<Unit>
}
