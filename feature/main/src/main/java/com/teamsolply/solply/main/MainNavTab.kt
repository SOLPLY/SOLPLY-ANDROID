package com.teamsolply.solply.main

import androidx.compose.runtime.Composable
import com.teamsolply.solply.home.navigation.Home
import com.teamsolply.solply.navigation.Route

internal enum class MainNavTab(
    val iconResId: Int,
    internal val contentDescription: String,
    val route: Route
) {
    HOME(
        iconResId = com.teamsolply.solply.designsystem.R.drawable.ic_bottom_nav_dummy,
        contentDescription = "HOME",
        Home
    ),
    DummyRoute1(
        iconResId = com.teamsolply.solply.designsystem.R.drawable.ic_bottom_nav_dummy,
        contentDescription = "DummyRoute1",
        Home
    ),
    DummyRoute2(
        iconResId = com.teamsolply.solply.designsystem.R.drawable.ic_bottom_nav_dummy,
        contentDescription = "DummyRoute2",
        Home
    ),
    DummyRoute3(
        iconResId = com.teamsolply.solply.designsystem.R.drawable.ic_bottom_nav_dummy,
        contentDescription = "DummyRoute3",
        Home
    ),
    DummyRoute4(
        iconResId = com.teamsolply.solply.designsystem.R.drawable.ic_bottom_nav_dummy,
        contentDescription = "DummyRoute4",
        Home
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