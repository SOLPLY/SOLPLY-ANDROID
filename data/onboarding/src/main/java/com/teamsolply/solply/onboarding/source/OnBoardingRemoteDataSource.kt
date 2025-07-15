package com.teamsolply.solply.onboarding.source

import com.teamsolply.solply.onboarding.dto.request.PatchUserInfoRequestDto
import com.teamsolply.solply.onboarding.dto.response.NicknameDuplicateResponseDto
import com.teamsolply.solply.onboarding.dto.response.PatchUserInfoResponseDto

interface OnBoardingRemoteDataSource {
    suspend fun checkNicknameDuplicate(nickname: String): NicknameDuplicateResponseDto
    suspend fun patchUserInfo(patchUserInfoRequestDto: PatchUserInfoRequestDto): PatchUserInfoResponseDto
}
