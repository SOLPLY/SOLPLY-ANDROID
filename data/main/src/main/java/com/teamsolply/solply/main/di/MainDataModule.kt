package com.teamsolply.solply.main.di

import com.teamsolply.solply.main.repository.MainRepository
import com.teamsolply.solply.main.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainDataModule {
    @Binds
    @Singleton
    abstract fun bindsMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository
}
