package com.teamsolply.solply.oauth.di

import com.teamsolply.solply.oauth.datasource.OauthRemoteDataSourceImpl
import com.teamsolply.solply.oauth.source.OauthRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OauthRemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindsOauthRemoteDataSource(oauthRemoteDataSource: OauthRemoteDataSourceImpl): OauthRemoteDataSource
}
