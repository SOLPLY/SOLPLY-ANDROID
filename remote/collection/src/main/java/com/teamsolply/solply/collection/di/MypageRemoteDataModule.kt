package com.teamsolply.solply.collection.di

import com.teamsolply.solply.collection.datasource.MypageRemoteDataSourceImpl
import com.teamsolply.solply.collection.source.MypageRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MypageRemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindsMypageRemoteDataSource(mypageRemoteDataSource: MypageRemoteDataSourceImpl): MypageRemoteDataSource
}
