package com.teamsolply.solply.main.di

import com.teamsolply.solply.main.datasource.MainLocalDataSourceImpl
import com.teamsolply.solply.main.source.MainLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainLocalDataModule {
    @Binds
    @Singleton
    abstract fun bindsMainLocalDataSource(mainLocalDataSource: MainLocalDataSourceImpl): MainLocalDataSource
}