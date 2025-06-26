package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.mypage.model.UserInfoEntity
import com.teamsolply.solply.mypage.source.MypageRemoteDataSource
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageRemoteDataSource: MypageRemoteDataSource
) : MypageRepository {
    override suspend fun getUserInfo(): Result<UserInfoEntity> = runCatching {
        mypageRemoteDataSource.getUserInfo()
    }.mapCatching {
        UserInfoEntity(it)
    }
}
