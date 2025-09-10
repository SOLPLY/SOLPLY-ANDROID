package com.teamsolply.solply.collection.di

import com.teamsolply.solply.collection.service.MypageService
import com.teamsolply.solply.network.di.AccessToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MypageServiceModule {
    @Provides
    @Singleton
    fun providesMypageService(@AccessToken retrofit: Retrofit): MypageService =
        retrofit.create(MypageService::class.java)
}
