package com.teamsolply.solply.main.datasource

import androidx.datastore.core.DataStore
import com.teamsolply.solply.datastore.SolplyTokenData
import com.teamsolply.solply.main.source.MainLocalDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MainLocalDataSourceImpl @Inject constructor(
    private val mainLocalDataSource: DataStore<SolplyTokenData>
) : MainLocalDataSource {
    override suspend fun getAutoSignIn(): Boolean = mainLocalDataSource.data.first().autoSignIn
}