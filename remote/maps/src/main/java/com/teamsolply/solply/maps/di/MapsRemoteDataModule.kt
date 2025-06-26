package com.teamsolply.solply.maps.di

import com.teamsolply.solply.maps.datasource.MapsRemoteDataSourceImpl
import com.teamsolply.solply.maps.source.MapsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapsRemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindsMapsRemoteDataSource(mapsRemoteDataSource: MapsRemoteDataSourceImpl): MapsRemoteDataSource
}
