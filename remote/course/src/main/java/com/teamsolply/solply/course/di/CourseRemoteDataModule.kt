package com.teamsolply.solply.course.di

import com.teamsolply.solply.course.datasource.CourseRemoteDataSourceImpl
import com.teamsolply.solply.course.source.CourseRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CourseRemoteDataModule {
    @Binds
    @Singleton
    abstract fun bindsCourseRemoteDataSource(courseRemoteDataSource: CourseRemoteDataSourceImpl): CourseRemoteDataSource
}