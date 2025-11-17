package com.teamsolply.solply.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NaverLocalSearchResponseDto(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<LocalSearchItem>
)

@Serializable
data class LocalSearchItem(
    val title: String,
    val link: String,
    val category: String,
    val description: String,
    val telephone: String,
    val address: String,
    val roadAddress: String,
    val mapx: String,
    val mapy: String
)