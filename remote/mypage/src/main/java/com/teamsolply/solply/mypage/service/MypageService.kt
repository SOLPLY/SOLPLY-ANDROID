package com.teamsolply.solply.mypage.service

import com.teamsolply.solply.mypage.dto.request.DeleteUserRequestDto
import com.teamsolply.solply.mypage.dto.response.GetPersonaListResponseDto
import com.teamsolply.solply.mypage.dto.response.GetUserInfoResponseDto
import com.teamsolply.solply.mypage.dto.response.GetWithdrawListResponseDto
import com.teamsolply.solply.mypage.dto.response.NicknameDuplicateResponseDto
import com.teamsolply.solply.mypage.dto.response.PlaceListResponseDto
import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.network.model.NullableBaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path
import retrofit2.http.Query

interface MypageService {
    @GET("/api/users")
    suspend fun getUserInfo(): BaseResponse<GetUserInfoResponseDto>

    @GET("api/places")
    suspend fun getPlaceList(
        @Query("townId") townId: Long,
        @Query("isBookmarkSearch") isBookmarkedSearch: Boolean = true
    ): BaseResponse<PlaceListResponseDto>

    @GET("api/users/{userId}/places")
    suspend fun getMyReportPlaceList(
        @Path("userId") userId: Long,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): BaseResponse<PlaceListResponseDto>

    @GET("api/users/persona")
    suspend fun getPersonaList(): BaseResponse<GetPersonaListResponseDto>

    @GET("api/users/check-nickname")
    suspend fun checkNicknameDuplicate(
        @Query("nickname") nickname: String
    ): BaseResponse<NicknameDuplicateResponseDto>

    @GET("api/users/withdraw/reasons")
    suspend fun getWithdrawList(): BaseResponse<GetWithdrawListResponseDto>

    @HTTP(method = "DELETE", path = "api/users/withdraw", hasBody = true)
    suspend fun deleteUser(
        @Body deleteUserRequestDto: DeleteUserRequestDto
    ): NullableBaseResponse<Unit>
}
