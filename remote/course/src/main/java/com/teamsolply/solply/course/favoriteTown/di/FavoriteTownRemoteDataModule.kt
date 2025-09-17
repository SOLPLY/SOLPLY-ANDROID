package com.teamsolply.solply.course.favoriteTown.di

import com.teamsolply.solply.course.favoriteTown.datasource.FavoriteTownRemoteDataSourceImpl
import com.teamsolply.solply.course.favoriteTown.source.FavoriteTownRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteTownRemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindsFavoriteTownRemoteDataSource(impl: FavoriteTownRemoteDataSourceImpl): FavoriteTownRemoteDataSource
}
