package com.teamsolply.solply.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.home.navigation.homeNavGraph
import com.teamsolply.solply.main.component.MainBottomBar
import com.teamsolply.solply.oauth.navigation.oauthNavGraph
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator = rememberMainNavigator()
) {
    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            NavHost(
                navController = navigator.navController,
                startDestination = navigator.startDestination,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                modifier = modifier
                    .background(color = SolplyTheme.colors.white)
                    .fillMaxSize()
            ) {
                oauthNavGraph(paddingValues = innerPadding)
                homeNavGraph(paddingValues = innerPadding)
            }
        },
        bottomBar = {
            MainBottomBar(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 8.dp, end = 8.dp, bottom = 10.dp),
                visible = navigator.setBottomBarVisibility(),
                tabs = MainNavTab.entries.toPersistentList(),
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) }
            )
        }
    )
}
