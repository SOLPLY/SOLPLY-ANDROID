package com.teamsolply.solply.course.favoriteTown.di

import com.teamsolply.solply.course.favoriteTown.repository.FavoriteTownRepository
import com.teamsolply.solply.course.favoriteTown.repository.FavoriteTownRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteTownDataModule {
    @Binds
    @Singleton
    abstract fun bindsFavoriteTownRepository(
        favoriteTownRepositoryImpl: FavoriteTownRepositoryImpl
    ): FavoriteTownRepository
}
