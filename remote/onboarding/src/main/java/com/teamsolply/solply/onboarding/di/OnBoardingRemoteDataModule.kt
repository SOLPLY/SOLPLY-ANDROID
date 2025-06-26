package com.teamsolply.solply.onboarding.di

import com.teamsolply.solply.onboarding.datasource.OnBoardingRemoteDataSourceImpl
import com.teamsolply.solply.onboarding.source.OnBoardingRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OnBoardingRemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindsOnBoardingRemoteDataSource(onBoardingRemoteDataSource: OnBoardingRemoteDataSourceImpl): OnBoardingRemoteDataSource
}