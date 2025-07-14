package com.teamsolply.solply.place.model

data class TagEntity(
    val tagId: Int,
    val tagType: String,
    val name: String,
    val parentId: Int
)
