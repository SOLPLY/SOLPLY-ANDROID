package com.teamsolply.solply.mypage.di

import com.teamsolply.solply.mypage.datasource.MypageLocalDataSource
import com.teamsolply.solply.mypage.datasource.MypageLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MypageLocalDataModule {
    @Binds
    @Singleton
    abstract fun bindsMypageLocalDataSource(mypageLocalDataSource: MypageLocalDataSourceImpl): MypageLocalDataSource
}
