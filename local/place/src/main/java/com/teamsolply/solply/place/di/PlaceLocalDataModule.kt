package com.teamsolply.solply.place.di

import com.teamsolply.solply.place.datasource.PlaceLocalDataSourceImpl
import com.teamsolply.solply.place.source.PlaceLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaceLocalDataModule {
    @Binds
    @Singleton
    abstract fun bindsPlaceLocalDataSource(placeLocalDataSource: PlaceLocalDataSourceImpl): PlaceLocalDataSource
}