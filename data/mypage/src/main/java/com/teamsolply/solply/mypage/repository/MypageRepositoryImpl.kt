package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.mypage.datasource.MypageRemoteDataSource
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageRemoteDataSource: MypageRemoteDataSource
) : MypageRepository
