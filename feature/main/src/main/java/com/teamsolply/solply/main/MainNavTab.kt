package com.teamsolply.solply.main

import androidx.compose.runtime.Composable
import com.teamsolply.solply.collection.navigation.Collection
import com.teamsolply.solply.course.navigation.Course
import com.teamsolply.solply.mypage.navigation.Mypage
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
        contentDescription = "수집함",
        route = Collection
    ),
    MYPAGE(
        contentDescription = "마이페이지",
        route = Mypage
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
