package com.teamsolply.solply.search.di

import com.teamsolply.solply.search.repository.SearchRepository
import com.teamsolply.solply.search.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchDataModule {
    @Binds
    @Singleton
    abstract fun bindsSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}
