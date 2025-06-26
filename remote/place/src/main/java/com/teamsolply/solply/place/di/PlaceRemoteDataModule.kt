package com.teamsolply.solply.place.di

import com.teamsolply.solply.place.datasource.PlaceRemoteDataSourceImpl
import com.teamsolply.solply.place.source.PlaceRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaceRemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindsPlaceRemoteDataSource(placeRemoteDataSource: PlaceRemoteDataSourceImpl): PlaceRemoteDataSource
}