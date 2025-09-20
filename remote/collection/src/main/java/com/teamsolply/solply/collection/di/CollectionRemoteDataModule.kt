package com.teamsolply.solply.collection.di

import com.teamsolply.solply.collection.datasource.CollectionRemoteDataSourceImpl
import com.teamsolply.solply.collection.source.CollectionRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CollectionRemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindsCollectionRemoteDataSource(collectionRemoteDataSource: CollectionRemoteDataSourceImpl): CollectionRemoteDataSource
}
