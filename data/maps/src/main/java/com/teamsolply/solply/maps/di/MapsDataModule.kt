package com.teamsolply.solply.maps.di

import com.teamsolply.solply.maps.repository.MapsRepository
import com.teamsolply.solply.maps.repository.MapsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapsDataModule {
    @Binds
    @Singleton
    abstract fun bindsMapsRepository(mapsRepositoryImpl: MapsRepositoryImpl): MapsRepository
}
