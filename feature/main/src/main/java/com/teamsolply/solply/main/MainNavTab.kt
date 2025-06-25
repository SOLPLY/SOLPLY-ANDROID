package com.teamsolply.solply.main

import androidx.compose.runtime.Composable
import com.teamsolply.solply.course.navigation.Course
import com.teamsolply.solply.navigation.Route
import com.teamsolply.solply.place.navigation.Place

internal enum class MainNavTab(
    val iconResId: Int,
    internal val contentDescription: String,
    val route: Route
) {
    PLACE(
        iconResId = com.teamsolply.solply.designsystem.R.drawable.ic_bottom_nav_dummy,
        contentDescription = "PLACE",
        Place
    ),
    COURSE(
        iconResId = com.teamsolply.solply.designsystem.R.drawable.ic_bottom_nav_dummy,
        contentDescription = "COURSE",
        Course
    ) ;

    companion object {
        @Composable
        fun find(predicate: @Composable (Route) -> Boolean): MainNavTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
