package com.teamsolply.solply.course.favoriteTown.model

data class CourseState(
    val selectedTownId: Int?,
    val townList: List<TownModel>
)
