package com.teamsolply.solply.main.component

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.Color
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
    AnimatedVisibility(visible = visible) {
        Box(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .background(
                        color = SolplyTheme.colors.gray300,
                        shape = CircleShape
                    )
                    .wrapContentWidth()
                    .align(Alignment.Center)
                    .height(50.dp),
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
        MainNavTab.PLACE -> PaddingValues(start = 4.dp)
        else -> PaddingValues(end = 4.dp)
    }

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .background(
                color = if (isSelected) SolplyTheme.colors.gray500 else SolplyTheme.colors.gray300,
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
            color = SolplyTheme.colors.black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 26.dp, vertical = 10.dp)
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
            .padding(end = 15.dp)
            .size(50.dp)
            .background(
                color = SolplyTheme.colors.gray300,
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
            tint = Color.Unspecified
        )
    }
}
