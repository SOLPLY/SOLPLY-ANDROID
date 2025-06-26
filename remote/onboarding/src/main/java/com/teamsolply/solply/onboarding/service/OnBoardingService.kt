package com.teamsolply.solply.onboarding.service

import com.teamsolply.solply.onboarding.dto.request.SignUpRequestDto

interface OnBoardingService {

    suspend fun signUp(
        signUpRequestDto: SignUpRequestDto
    )
}