package com.teamsolply.solply.onboarding.datasource

import com.teamsolply.solply.onboarding.dto.request.PatchUserInfoRequestDto
import com.teamsolply.solply.onboarding.dto.response.GetPersonaQuestionsResponseDto
import com.teamsolply.solply.onboarding.dto.response.NicknameDuplicateResponseDto
import com.teamsolply.solply.onboarding.dto.response.PatchUserInfoResponseDto
import com.teamsolply.solply.onboarding.service.OnBoardingService
import com.teamsolply.solply.onboarding.source.remote.OnBoardingRemoteDataSource
import javax.inject.Inject

class OnBoardingRemoteDataSourceImpl @Inject constructor(
    private val onBoardingService: OnBoardingService
) : OnBoardingRemoteDataSource {
    override suspend fun getPersonaQuestions(): GetPersonaQuestionsResponseDto =
        onBoardingService.getPersonaQuestions().data

    override suspend fun checkNicknameDuplicate(nickname: String): NicknameDuplicateResponseDto =
        onBoardingService.checkNicknameDuplicate(nickname = nickname).data

    override suspend fun patchUserInfo(patchUserInfoRequestDto: PatchUserInfoRequestDto): PatchUserInfoResponseDto =
        onBoardingService.patchUserInfo(patchUserInfoRequestDto = patchUserInfoRequestDto).data
}
