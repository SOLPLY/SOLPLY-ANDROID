package com.teamsolply.solply.onboarding.service

import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.onboarding.dto.response.NicknameDuplicateResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OnBoardingService {

    @GET("api/users/check-nickname")
    suspend fun checkNicknameDuplicate(
        @Query("nickname") nickname: String
    ): BaseResponse<NicknameDuplicateResponseDto>
}
