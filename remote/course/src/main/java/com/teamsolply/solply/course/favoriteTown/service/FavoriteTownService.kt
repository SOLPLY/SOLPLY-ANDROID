package com.teamsolply.solply.course.favoriteTown.service

import com.teamsolply.solply.course.favoriteTown.dto.FavoriteTownUserInfoResponseDto
import com.teamsolply.solply.network.model.BaseResponse
import retrofit2.http.GET

interface FavoriteTownService {
    @GET("/api/users/towns")
    suspend fun getTownList(): BaseResponse<FavoriteTownUserInfoResponseDto>
}