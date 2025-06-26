package com.teamsolply.solply.mypage.di

import com.teamsolply.solply.mypage.service.MypageService
import com.teamsolply.solply.network.di.Auth
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
    fun providesMypageService(@Auth retrofit: Retrofit): MypageService =
        retrofit.create(MypageService::class.java)
}
