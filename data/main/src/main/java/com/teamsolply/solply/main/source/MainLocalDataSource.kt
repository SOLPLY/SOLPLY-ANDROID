package com.teamsolply.solply.main.source

interface MainLocalDataSource {
    suspend fun getAutoSignIn(): Boolean
}
