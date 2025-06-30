package com.teamsolply.solply.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccessToken

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoneAccessToken
