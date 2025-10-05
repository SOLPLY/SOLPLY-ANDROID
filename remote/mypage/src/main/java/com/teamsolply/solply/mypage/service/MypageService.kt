package com.teamsolply.solply.mypage.service

import com.teamsolply.solply.mypage.dto.response.GetPersonaListResponseDto
import com.teamsolply.solply.mypage.dto.response.GetUserInfoResponseDto
import com.teamsolply.solply.mypage.dto.response.NicknameDuplicateResponseDto
import com.teamsolply.solply.mypage.dto.response.PlaceListResponseDto
import com.teamsolply.solply.network.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MypageService {
    @GET("/api/users")
    suspend fun getUserInfo(): BaseResponse<GetUserInfoResponseDto>

    @GET("api/places")
    suspend fun getPlaceList(
        @Query("townId") townId: Long,
        @Query("isBookmarkSearch") isBookmarkedSearch: Boolean = true
    ): BaseResponse<PlaceListResponseDto>

    @GET("api/users/persona")
    suspend fun getPersonaList(): BaseResponse<GetPersonaListResponseDto>

    @GET("api/users/check-nickname")
    suspend fun checkNicknameDuplicate(
        @Query("nickname") nickname: String
    ): BaseResponse<NicknameDuplicateResponseDto>
}
