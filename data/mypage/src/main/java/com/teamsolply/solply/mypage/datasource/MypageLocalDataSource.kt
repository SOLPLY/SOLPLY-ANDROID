package com.teamsolply.solply.mypage.datasource

interface MypageLocalDataSource {
    suspend fun saveAutoSignIn(autoSignIn: Boolean)
}