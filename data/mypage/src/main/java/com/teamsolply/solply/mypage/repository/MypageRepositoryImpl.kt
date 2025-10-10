package com.teamsolply.solply.mypage.repository

import android.util.Log
import com.teamsolply.solply.mypage.datasource.MypageRemoteDataSource
import com.teamsolply.solply.mypage.dto.response.toDomain
import com.teamsolply.solply.mypage.model.PersonaEntity
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.mypage.model.UserInfo
import com.teamsolply.solply.mypage.model.WithdrawEntity
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageRemoteDataSource: MypageRemoteDataSource
) : MypageRepository {
    override suspend fun getUserInfo(): Result<UserInfo> = runCatching {
        mypageRemoteDataSource.getUserInfo()
    }.mapCatching { userDto ->
        userDto.toDomain()
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

    override suspend fun getPersonaList(): Result<List<PersonaEntity>> = runCatching {
        mypageRemoteDataSource.getPersonaList().personaDtoList
    }.mapCatching { personaList ->
        Log.d("persona: ", "repo impl start")
        personaList.map { persona ->
            PersonaEntity(
                personaType = persona.personaType,
                description = persona.description
            )
        }
    }

    override suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean> = runCatching {
        mypageRemoteDataSource.checkNicknameDuplicate(nickname = nickname).isDuplicated
    }

    override suspend fun getWithdrawList(): Result<List<WithdrawEntity>> = runCatching {
        mypageRemoteDataSource.getWithdrawList().withdrawList
    }.mapCatching { list ->
        list.map { reason ->
            WithdrawEntity(
                withdrawType = reason.withdrawType,
                description = reason.description
            )
        }
    }
}
