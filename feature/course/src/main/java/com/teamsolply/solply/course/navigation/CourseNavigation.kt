package com.teamsolply.solply.course.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.course.CourseRoute
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateCourse(
    navOptions: NavOptions
) {
    navigate(Course, navOptions)
}

fun NavGraphBuilder.courseNavGraph(
    paddingValues: PaddingValues
) {
    composable<Course> {
        CourseRoute(
            paddingValues = paddingValues
        )
    }
}

@Serializable
data object Course : Route