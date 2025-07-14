package com.teamsolply.solply.mypage.collection.course

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateCourseCollection(
    navOptions: NavOptions
) {
    navigate(CourseCollection, navOptions)
}

fun NavGraphBuilder.courseCollectionNavGraph(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    navigateToBack: () -> Unit
) {
    composable<CourseCollection> {
        CourseCollectionRoute(
            paddingValues = paddingValues,
            navigateToMaps = navigateToMaps,
            navigateToBack = navigateToBack
        )
    }
}

@Serializable
data object CourseCollection : Route
