package com.teamsolply.solply.place.di

import com.teamsolply.solply.network.di.AccessToken
import com.teamsolply.solply.place.service.PlaceService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlaceServiceModule {
    @Provides
    @Singleton
    fun providesPlaceService(@AccessToken retrofit: Retrofit): PlaceService =
        retrofit.create(PlaceService::class.java)
}
