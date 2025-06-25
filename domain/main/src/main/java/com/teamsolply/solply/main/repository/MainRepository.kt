package com.teamsolply.solply.main.repository

interface MainRepository {
    suspend fun getAutoSignIn(): Result<Boolean>
}