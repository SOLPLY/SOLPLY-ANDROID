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
    paddingValues: PaddingValues,
    navigateToMaps: (String, Long, Long) -> Unit,
) {
    composable<Course> {
        CourseRoute(
            paddingValues = paddingValues,
            navigateToMaps = navigateToMaps,
        )
    }
}

@Serializable
data object Course : Route
