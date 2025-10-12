package com.teamsolply.solply.mypage.datasource

import androidx.datastore.core.DataStore
import com.teamsolply.solply.datastore.SolplyTokenData
import javax.inject.Inject

class MypageLocalDataSourceImpl @Inject constructor(
    private val mypageLocalDataSource: DataStore<SolplyTokenData>
) : MypageLocalDataSource {
    override suspend fun saveAutoSignIn(autoSignIn: Boolean) {
        mypageLocalDataSource.updateData { old ->
            old.copy(
                autoSignIn = autoSignIn
            )
        }
    }
}
