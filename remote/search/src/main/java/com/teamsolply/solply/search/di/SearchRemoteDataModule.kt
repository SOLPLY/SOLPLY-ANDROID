package com.teamsolply.solply.search.di

import com.teamsolply.solply.search.datasource.SearchRemoteDataSourceImpl
import com.teamsolply.solply.search.source.SearchRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindsSearchRemoteDataSource(searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl): SearchRemoteDataSource
}
