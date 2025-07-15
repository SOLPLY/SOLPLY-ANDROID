package com.teamsolply.solply.onboarding.datasource

import com.teamsolply.solply.onboarding.dto.response.NicknameDuplicateResponseDto
import com.teamsolply.solply.onboarding.service.OnBoardingService
import com.teamsolply.solply.onboarding.source.OnBoardingRemoteDataSource
import javax.inject.Inject

class OnBoardingRemoteDataSourceImpl @Inject constructor(
    private val onBoardingService: OnBoardingService
) : OnBoardingRemoteDataSource {
    override suspend fun checkNicknameDuplicate(nickname: String): NicknameDuplicateResponseDto =
        onBoardingService.checkNicknameDuplicate(nickname = nickname).data

}
