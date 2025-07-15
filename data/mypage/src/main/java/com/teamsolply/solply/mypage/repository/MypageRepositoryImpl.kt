package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.model.CourseInfoEntity
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.mypage.model.TownEntity
import com.teamsolply.solply.mypage.source.MypageRemoteDataSource
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageRemoteDataSource: MypageRemoteDataSource
) : MypageRepository {
    override suspend fun getPlaceTownList(): Result<List<TownEntity>> = runCatching {
        mypageRemoteDataSource.getPlaceTownList()
        listOf(
            TownEntity(
                townId = 1,
                townName = "연희동",
                tagList = listOf(
                    PlaceType.BOOK,
                    PlaceType.CAFE
                ),
                courseName = "오감으로 수집하는 코스",
                imageUrl = ""
            ),
            TownEntity(
                townId = 2,
                townName = "망원동",
                tagList = listOf(
                    PlaceType.WALK,
                    PlaceType.CAFE
                ),
                courseName = "오감으로 수집하는 코스",
                imageUrl = ""
            ),
            TownEntity(
                townId = 3,
                townName = "성수동",
                tagList = listOf(
                    PlaceType.FOOD,
                    PlaceType.CAFE
                ),
                courseName = "오감으로 수집하는 코스",
                imageUrl = ""
            )
        )
    }

    override suspend fun getCourseTownList(): Result<List<TownEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlaceList(): Result<List<PlaceInfoEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCourseList(): Result<List<CourseInfoEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlaces(placeIds: List<Int>): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCourses(courseIds: List<Int>): Result<Unit> {
        TODO("Not yet implemented")
    }
}

