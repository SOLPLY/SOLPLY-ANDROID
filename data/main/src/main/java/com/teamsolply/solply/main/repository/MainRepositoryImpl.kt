package com.teamsolply.solply.main.repository

import com.teamsolply.solply.main.source.MainLocalDataSource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainLocalDataSource: MainLocalDataSource
) : MainRepository {
    override suspend fun getAutoSignIn(): Result<Boolean> = runCatching {
        mainLocalDataSource.getAutoSignIn()
    }
}
