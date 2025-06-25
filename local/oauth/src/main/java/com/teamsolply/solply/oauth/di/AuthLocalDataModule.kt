package com.teamsolply.solply.oauth.di

import com.teamsolply.solply.oauth.datasource.OauthLocalDataSourceImpl
import com.teamsolply.solply.oauth.source.OauthLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthLocalDataModule {
    @Binds
    @Singleton
    abstract fun bindsOauthLocalDataSource(authLocalDataSource: OauthLocalDataSourceImpl): OauthLocalDataSource
}