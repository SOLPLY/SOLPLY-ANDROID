package com.teamsolply.solply.onboarding.service

import retrofit2.http.POST

interface OnBoardingService {

    @POST("")
    suspend fun signUp()
}