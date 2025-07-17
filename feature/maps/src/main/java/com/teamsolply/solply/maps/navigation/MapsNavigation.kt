package com.teamsolply.solply.maps.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.teamsolply.solply.maps.MapsRoute
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateMaps(
    mapsType: String,
    townId: Long,
    placeId: Long?,
    courseId: Long?,
    navOptions: NavOptions
) {
    navigate(
        Maps(mapsType = mapsType, townId = townId, placeId = placeId, courseId = courseId),
        navOptions
    )
}

fun NavGraphBuilder.mapsNavGraph(
    paddingValues: PaddingValues,
    showTextSnackBar: (String) -> Unit,
    showNotificationSnackBar: (String) -> Unit,
    showNavigateSnackBar: (String, () -> Unit) -> Unit,
    navigateToPlaceDetail: () -> Unit,
    navigateToEditCourse: () -> Unit,
    navigateToPlace: () -> Unit,
    navigateToCourse: () -> Unit,
    navigateToMypage: () -> Unit,
    navigateToBack: () -> Unit
) {
    composable<Maps> { backStackEntry ->
        val mapsType = MapsType.valueOf(backStackEntry.toRoute<Maps>().mapsType)
        val townId = backStackEntry.toRoute<Maps>().townId
        val placeId = backStackEntry.toRoute<Maps>().placeId
        val courseId = backStackEntry.toRoute<Maps>().courseId

        MapsRoute(
            mapsType = mapsType,
            townId = townId,
            placeId = placeId,
            courseId = courseId,
            showTextSnackBar = showTextSnackBar,
            showNotificationSnackBar = showNotificationSnackBar,
            showNavigateSnackBar = showNavigateSnackBar,
            navigateToPlaceDetail = navigateToPlaceDetail,
            navigateToEditCourse = navigateToEditCourse,
            navigateToPlace = navigateToPlace,
            navigateToCourse = navigateToCourse,
            navigateToMypage = navigateToMypage,
            paddingValues = paddingValues,
            navigateToBack = navigateToBack
        )
    }
}

@Serializable
data class Maps(
    val mapsType: String,
    val townId: Long,
    val placeId: Long?,
    val courseId: Long?
) : Route
