package com.teamsolply.solply.oauth.di

import com.teamsolply.solply.network.di.NoneAccessToken
import com.teamsolply.solply.oauth.service.OauthService
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
    fun providesOauthService(@NoneAccessToken retrofit: Retrofit): OauthService =
        retrofit.create(OauthService::class.java)
}
