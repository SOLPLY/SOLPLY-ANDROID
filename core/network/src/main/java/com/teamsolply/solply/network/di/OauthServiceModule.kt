package com.teamsolply.solply.network.di

import com.teamsolply.solply.network.service.TokenRefreshService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OauthServiceModule {
    @Provides
    @Singleton
    fun providesTokenRefreshService(@NoneAccessToken retrofit: Retrofit): TokenRefreshService =
        retrofit.create(TokenRefreshService::class.java)
}