package com.teamsolply.solply.mypage.collection.course

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateCourseCollection(
    townId: Int,
    townName: String,
    navOptions: NavOptions
) {
    navigate(CourseCollection(townId = townId, townName = townName), navOptions)
}

fun NavGraphBuilder.courseCollectionNavGraph(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    navigateToBack: () -> Unit
) {
    composable<CourseCollection> { backStackEntry ->
        CourseCollectionRoute(
            paddingValues = paddingValues,
            townId = backStackEntry.toRoute<CourseCollection>().townId,
            townName = backStackEntry.toRoute<CourseCollection>().townName,
            navigateToMaps = navigateToMaps,
            navigateToBack = navigateToBack
        )
    }
}

@Serializable
data class CourseCollection(val townId: Int, val townName: String) : Route
