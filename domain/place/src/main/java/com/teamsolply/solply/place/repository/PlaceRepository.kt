package com.teamsolply.solply.place.repository

interface PlaceRepository {
    suspend fun getAutoSignIn(): Result<Boolean>
}