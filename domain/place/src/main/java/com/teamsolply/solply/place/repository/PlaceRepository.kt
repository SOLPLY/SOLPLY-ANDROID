package com.teamsolply.solply.place.repository

import com.teamsolply.solply.place.model.AutoSignInEntity

interface PlaceRepository {
    suspend fun saveAutoSignIn(autoSignIn: AutoSignInEntity): Result<Unit>
}