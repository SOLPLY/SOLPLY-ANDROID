package com.teamsolply.solply.onboarding.service

import com.teamsolply.solply.network.model.BaseResponse
import com.teamsolply.solply.onboarding.dto.request.PatchUserInfoRequestDto
import com.teamsolply.solply.onboarding.dto.response.GetAllTownResponseDto
import com.teamsolply.solply.onboarding.dto.response.GetPersonaQuestionsResponseDto
import com.teamsolply.solply.onboarding.dto.response.NicknameDuplicateResponseDto
import com.teamsolply.solply.onboarding.dto.response.PatchUserInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface OnBoardingService {

    @GET("api/users/persona")
    suspend fun getPersonaQuestions(): BaseResponse<GetPersonaQuestionsResponseDto>

    @GET("api/towns")
    suspend fun getAllTowns(): BaseResponse<GetAllTownResponseDto>

    @GET("api/users/check-nickname")
    suspend fun checkNicknameDuplicate(
        @Query("nickname") nickname: String
    ): BaseResponse<NicknameDuplicateResponseDto>

    @PATCH("api/users/onboarding")
    suspend fun patchUserInfo(
        @Body patchUserInfoRequestDto: PatchUserInfoRequestDto
    ): BaseResponse<PatchUserInfoResponseDto>
}
