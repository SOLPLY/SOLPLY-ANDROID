package com.teamsolply.solply.datastore

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class SolplyLocalData(
    val autoSignIn: Boolean = false,
)