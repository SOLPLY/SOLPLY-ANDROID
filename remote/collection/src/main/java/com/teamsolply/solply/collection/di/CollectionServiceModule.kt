package com.teamsolply.solply.collection.di

import com.teamsolply.solply.collection.service.CollectionService
import com.teamsolply.solply.network.di.AccessToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CollectionServiceModule {
    @Provides
    @Singleton
    fun providesMypageService(@AccessToken retrofit: Retrofit): CollectionService =
        retrofit.create(CollectionService::class.java)
}
