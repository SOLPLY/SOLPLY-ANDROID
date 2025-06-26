package com.teamsolply.solply.course.di

import com.teamsolply.solply.course.service.CourseService
import com.teamsolply.solply.network.di.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CourseServiceModule {
    @Provides
    @Singleton
    fun providesCourseService(@Auth retrofit: Retrofit): CourseService =
        retrofit.create(CourseService::class.java)
}