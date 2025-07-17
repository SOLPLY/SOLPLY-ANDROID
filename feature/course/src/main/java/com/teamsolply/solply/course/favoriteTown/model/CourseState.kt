package com.teamsolply.solply.course.favoriteTown.model

data class CourseState(
    val selectedTownId: Long?,
    val townList: List<TownModel>
)
