package com.teamsolply.solply.mypage.source

interface MypageRemoteDataSource {
    suspend fun getUserInfo(): String
}