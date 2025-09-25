package com.teamsolply.solply.collection.datasource

import com.teamsolply.solply.collection.service.CollectionService
import com.teamsolply.solply.collection.source.CollectionRemoteDataSource
import javax.inject.Inject

class CollectionRemoteDataSourceImpl @Inject constructor(
    private val collectionService: CollectionService
) : CollectionRemoteDataSource {
    override suspend fun getPlaceTownList() = collectionService.getPlaceTownList().data

    override suspend fun getCourseTownList() = collectionService.getCourseTownList().data

    override suspend fun getPlaceList(townId: Long) =
        collectionService.getPlaceList(townId = townId).data

    override suspend fun getCourseList(townId: Long) =
        collectionService.getCourseList(townId = townId).data

    override suspend fun deletePlaces(placeList: List<Long>) = collectionService.deletePlaces(placeList)

    override suspend fun deleteCourses(courseList: List<Long>) =
        collectionService.deleteCourses(courseList)
}
