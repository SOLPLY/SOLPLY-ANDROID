package com.teamsolply.solply.search.di

import com.teamsolply.solply.network.di.AccessToken
import com.teamsolply.solply.search.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchServiceModule {
    @Provides
    @Singleton
    fun providesSearchService(@AccessToken retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)
}
