package com.teamsolply.solply.maps.source

interface MapsRemoteDataSource {
    suspend fun saveCourse(courseInfo: String)
}