package com.teamsolply.solply.course.favoriteTown.di

import com.teamsolply.solply.course.favoriteTown.service.FavoriteTownService
import com.teamsolply.solply.course.service.CourseService
import com.teamsolply.solply.network.di.AccessToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteTownServiceModule {

    @Provides
    @Singleton
    fun provideFavoriteTownService(@AccessToken retrofit: Retrofit): FavoriteTownService {
        return retrofit.create(FavoriteTownService::class.java)
    }
}