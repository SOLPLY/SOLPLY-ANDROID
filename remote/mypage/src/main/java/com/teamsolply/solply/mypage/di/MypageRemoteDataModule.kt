package com.teamsolply.solply.mypage.di

import com.teamsolply.solply.mypage.datasource.MypageRemoteDataSourceImpl
import com.teamsolply.solply.mypage.source.MypageRemoteDataSource
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
