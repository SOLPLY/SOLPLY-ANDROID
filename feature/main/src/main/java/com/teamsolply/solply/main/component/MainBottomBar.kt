package com.teamsolply.solply.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.main.MainNavTab
import com.teamsolply.solply.ui.extension.customClickable
import kotlinx.collections.immutable.PersistentList

@Composable
internal fun MainBottomBar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    tabs: PersistentList<MainNavTab>,
    currentTab: MainNavTab?,
    onTabSelected: (MainNavTab) -> Unit
) {
    val transitionState = remember { androidx.compose.animation.core.MutableTransitionState(false) }
    androidx.compose.runtime.LaunchedEffect(visible) {
        transitionState.targetState = visible
    }

    AnimatedVisibility(
        visibleState = transitionState,
        enter = slideInVertically(
            animationSpec = spring(
                dampingRatio = 0.9f,
                stiffness = 600f
            ),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            animationSpec = spring(
                dampingRatio = 0.9f,
                stiffness = 600f
            ),
            targetOffsetY = { it }
        )
    ) {
        Row(
            modifier = modifier
                .background(
                    color = SolplyTheme.colors.gray900,
                    shape = CircleShape
                )
                .height(60.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEach { tab ->
                TabItem(
                    tab = tab,
                    isSelected = tab == currentTab,
                    onClick = { onTabSelected(tab) }
                )
            }
        }
    }
}

@Composable
private fun TabItem(
    tab: MainNavTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val iconResource = when (tab) {
        MainNavTab.PLACE -> if (isSelected) R.drawable.ic_bottom_bar_place_selected else R.drawable.ic_bottom_bar_place
        MainNavTab.COURSE -> if (isSelected) R.drawable.ic_bottom_bar_course_selected else R.drawable.ic_bottom_bar_course
        MainNavTab.COLLECTION -> if (isSelected) R.drawable.ic_bottom_bar_mypage_selected else R.drawable.ic_bottom_bar_mypage
        MainNavTab.MYPAGE -> if (isSelected) R.drawable.ic_bottom_bar_user_selected else R.drawable.ic_bottom_bar_user
    }
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) SolplyTheme.colors.green100 else SolplyTheme.colors.gray900,
                shape = CircleShape
            )
            .size(48.dp)
            .wrapContentWidth()
            .customClickable(
                rippleEnabled = false,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = "",
            tint = Color.Unspecified
        )
    }
}
