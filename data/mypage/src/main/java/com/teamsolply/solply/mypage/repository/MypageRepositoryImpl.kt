package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.mypage.model.TownCard
import com.teamsolply.solply.mypage.model.UserInfoEntity
import com.teamsolply.solply.mypage.source.MypageRemoteDataSource
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageRemoteDataSource: MypageRemoteDataSource
) : MypageRepository {
    override suspend fun getPlaceTownList(): Result<List<TownCard>> = runCatching {
        listOf(
            TownCard(
                townName = "연희동",
                imageUrl = ""
            ),
            TownCard(
                townName = "망원동",
                imageUrl = ""
            ),
            TownCard(
                townName = "성수동",
                imageUrl = ""
            )
        )
    }
}

