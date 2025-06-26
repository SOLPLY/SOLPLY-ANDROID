package com.teamsolply.solply.onboarding.di

import com.teamsolply.solply.onboarding.repository.OnBoardingRepository
import com.teamsolply.solply.onboarding.repository.OnBoardingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OnBoardingDataModule {
    @Binds
    @Singleton
    abstract fun bindsOnBoardingRepository(onBoardingRepositoryImpl: OnBoardingRepositoryImpl): OnBoardingRepository
}