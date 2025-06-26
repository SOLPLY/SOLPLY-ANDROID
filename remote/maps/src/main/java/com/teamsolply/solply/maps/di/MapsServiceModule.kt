package com.teamsolply.solply.maps.di

import com.teamsolply.solply.maps.service.MapsService
import com.teamsolply.solply.network.di.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapsServiceModule {
    @Provides
    @Singleton
    fun providesMapsService(@Auth retrofit: Retrofit): MapsService =
        retrofit.create(MapsService::class.java)
}
