package com.teamsolply.solply.collection.source

import com.teamsolply.solply.collection.dto.response.CourseListResponseDto
import com.teamsolply.solply.collection.dto.response.CourseTownListResponseDto
import com.teamsolply.solply.collection.dto.response.PlaceListResponseDto
import com.teamsolply.solply.collection.dto.response.PlaceTownListResponseDto
import com.teamsolply.solply.network.model.NullableBaseResponse

interface CollectionRemoteDataSource {
    suspend fun getPlaceTownList(): PlaceTownListResponseDto
    suspend fun getCourseTownList(): CourseTownListResponseDto
    suspend fun getPlaceList(townId: Long): PlaceListResponseDto
    suspend fun getCourseList(townId: Long): CourseListResponseDto
    suspend fun deletePlaces(placeList: List<Long>): NullableBaseResponse<Unit>
    suspend fun deleteCourses(courseList: List<Long>): NullableBaseResponse<Unit>
}
