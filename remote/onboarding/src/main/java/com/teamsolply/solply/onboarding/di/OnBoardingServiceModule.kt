package com.teamsolply.solply.onboarding.di

import com.teamsolply.solply.network.di.Auth
import com.teamsolply.solply.onboarding.service.OnBoardingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingServiceModule {
    @Provides
    @Singleton
    fun providesOnBoardingService(@Auth retrofit: Retrofit): OnBoardingService =
        retrofit.create(OnBoardingService::class.java)
}