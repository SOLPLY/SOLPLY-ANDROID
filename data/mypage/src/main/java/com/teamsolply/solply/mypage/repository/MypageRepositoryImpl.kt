package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.mypage.datasource.MypageRemoteDataSource
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.mypage.model.SelectedTownInfo
import com.teamsolply.solply.mypage.model.UserInfo
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageRemoteDataSource: MypageRemoteDataSource
) : MypageRepository {
    override suspend fun getUserInfo(): Result<UserInfo> = runCatching {
        mypageRemoteDataSource.getUserInfo()
    }.mapCatching { userDto ->
        UserInfo(
            userId = userDto.userId,
            nickname = userDto.nickname,
            selectedTown = SelectedTownInfo(
                townId = userDto.selectedTown.townId,
                townName = userDto.selectedTown.townName
            ),
            persona = userDto.persona
        )
    }

    override suspend fun getPlaceList(townId: Long): Result<List<PlaceInfoEntity>> = runCatching {
        mypageRemoteDataSource.getPlaceList(townId).placeList
    }.mapCatching { placeList ->
        placeList.map { place ->
            PlaceInfoEntity(
                placeId = place.placeId,
                placeName = place.placeName,
                placeType = place.tag,
                imageUrls = place.imageUrl,
                isSaved = place.isSaved
            )
        }
    }
}
