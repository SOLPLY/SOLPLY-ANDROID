package com.teamsolply.solply.oauth.di

import com.teamsolply.solply.oauth.repository.OauthRepository
import com.teamsolply.solply.oauth.repository.OauthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OauthDataModule {
    @Binds
    @Singleton
    abstract fun bindsOauthRepository(oauthRepositoryImpl: OauthRepositoryImpl): OauthRepository
}
