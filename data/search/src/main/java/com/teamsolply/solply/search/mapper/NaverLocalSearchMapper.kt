package com.teamsolply.solply.search.mapper

import com.teamsolply.solply.network.model.LocalSearchItem
import com.teamsolply.solply.network.model.NaverLocalSearchResponseDto
import com.teamsolply.solply.search.model.LocalSearchItemEntity
import com.teamsolply.solply.search.model.NaverLocalSearchResponseEntity

fun NaverLocalSearchResponseDto.toEntity() = NaverLocalSearchResponseEntity(
    lastBuildDate = lastBuildDate,
    total = total,
    start = start,
    display = display,
    items = items.map { it.toEntity() }
)

fun LocalSearchItem.toEntity() = LocalSearchItemEntity(
    title = title,
    link = link,
    category = category,
    description = description,
    telephone = telephone,
    address = address,
    roadAddress = roadAddress,
    mapx = mapx,
    mapy = mapy
)