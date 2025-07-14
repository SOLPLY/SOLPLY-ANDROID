package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.mypage.model.TownCard

interface MypageRepository {
    suspend fun getPlaceTownList(): Result<List<TownCard>>
}
