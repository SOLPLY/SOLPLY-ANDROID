package com.teamsolply.solply.mypage.di

import com.teamsolply.solply.mypage.repository.MypageRepository
import com.teamsolply.solply.mypage.repository.MypageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MypageDataModule {
    @Binds
    @Singleton
    abstract fun bindsMypageRepository(mypageRepositoryImpl: MypageRepositoryImpl): MypageRepository
}