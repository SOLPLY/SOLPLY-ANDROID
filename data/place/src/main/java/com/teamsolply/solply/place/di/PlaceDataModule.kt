package com.teamsolply.solply.place.di

import com.teamsolply.solply.place.repository.PlaceRepository
import com.teamsolply.solply.place.repository.PlaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaceDataModule {
    @Binds
    @Singleton
    abstract fun bindsPlaceRepository(placeRepositoryImpl: PlaceRepositoryImpl): PlaceRepository
}
