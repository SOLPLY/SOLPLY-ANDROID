package com.teamsolply.solply.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            animationSpec = tween(250),
            initialOffsetX = { it }
        ),
        exit = slideOutHorizontally(
            animationSpec = tween(250),
            targetOffsetX = { it }
        )) {
        Box(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .background(
                        color = SolplyTheme.colors.gray900,
                        shape = CircleShape
                    )
                    .wrapContentWidth()
                    .align(Alignment.Center)
                    .height(60.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.filter { it != MainNavTab.MYPAGE }.forEach { tab ->
                    TabItem(
                        tab = tab,
                        isSelected = tab == currentTab,
                        onClick = { onTabSelected(tab) }
                    )
                }
            }
            MypageButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = { onTabSelected(MainNavTab.MYPAGE) }
            )
        }
    }
}

@Composable
private fun TabItem(
    tab: MainNavTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val paddingValues = when (tab) {
        MainNavTab.PLACE -> PaddingValues(start = 6.dp)
        else -> PaddingValues(end = 6.dp)
    }

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .background(
                color = if (isSelected) SolplyTheme.colors.green100 else SolplyTheme.colors.gray900,
                shape = CircleShape
            )
            .wrapContentWidth()
            .customClickable(
                rippleEnabled = false,
                onClick = onClick
            )
    ) {
        Text(
            text = tab.contentDescription,
            style = SolplyTheme.typography.button16M,
            color = if (isSelected) SolplyTheme.colors.black else SolplyTheme.colors.gray100,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 14.dp)
        )
    }
}

@Composable
private fun MypageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(end = 11.dp)
            .size(60.dp)
            .background(
                color = SolplyTheme.colors.gray900,
                shape = CircleShape
            )
            .customClickable(
                rippleEnabled = false,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_mypage),
            contentDescription = "mypage",
            tint = SolplyTheme.colors.green100
        )
    }
}
