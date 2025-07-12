package com.teamsolply.solply.maps.model

sealed interface CourseSaveType {
    data object SaveToExistingCourse : CourseSaveType
    data object SaveAsNewCourse : CourseSaveType
}