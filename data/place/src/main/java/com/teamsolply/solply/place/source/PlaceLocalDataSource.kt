package com.teamsolply.solply.place.source

interface PlaceLocalDataSource {
    suspend fun saveAutoSignIn(autoSignIn: Boolean)
}
