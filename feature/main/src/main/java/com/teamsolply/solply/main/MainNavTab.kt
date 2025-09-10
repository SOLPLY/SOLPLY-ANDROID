package com.teamsolply.solply.main

import androidx.compose.runtime.Composable
import com.teamsolply.solply.collection.navigation.Collection
import com.teamsolply.solply.course.navigation.Course
import com.teamsolply.solply.navigation.Route
import com.teamsolply.solply.place.navigation.Place

internal enum class MainNavTab(
    internal val contentDescription: String,
    val route: Route
) {
    PLACE(
        contentDescription = "장소",
        route = Place
    ),
    COURSE(
        contentDescription = "코스",
        route = Course
    ),
    COLLECTION(
        contentDescription = "마이페이지",
        route = Collection
    ),
    MYPAGE(
        contentDescription = "유저",
        route = Collection
    );

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
