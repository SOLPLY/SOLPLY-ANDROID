package com.teamsolply.solply.onboarding.source

import com.teamsolply.solply.onboarding.dto.response.NicknameDuplicateResponseDto

interface OnBoardingRemoteDataSource {
    suspend fun checkNicknameDuplicate(nickname: String): NicknameDuplicateResponseDto
}
