package com.teamsolply.solply.collection.di

import com.teamsolply.solply.collection.repository.CollectionRepository
import com.teamsolply.solply.collection.repository.CollectionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CollectionDataModule {
    @Binds
    @Singleton
    abstract fun bindsMypageRepository(mypageRepositoryImpl: CollectionRepositoryImpl): CollectionRepository
}
