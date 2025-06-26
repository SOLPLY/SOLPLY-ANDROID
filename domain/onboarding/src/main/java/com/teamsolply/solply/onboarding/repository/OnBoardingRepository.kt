package com.teamsolply.solply.onboarding.repository

import com.teamsolply.solply.onboarding.model.SignUpEntity

interface OnBoardingRepository {
    suspend fun signUp(signUpInfo: SignUpEntity): Result<Unit>
}