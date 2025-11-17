package com.teamsolply.solply.designsystem.component.dropdown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun SolplyBasicDropDown(
    defaultLabel: @Composable RowScope.() -> Unit,
    onClickDropIcon: () -> Unit,
    modifier: Modifier = Modifier,
    isDropped: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .clip(
                RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = SolplyTheme.colors.gray300,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isDropped) {
                        Modifier.background(
                            color = SolplyTheme.colors.gray100
                        )
                    } else {
                        Modifier.background(
                            color = SolplyTheme.colors.white
                        )
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            defaultLabel()
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down_lg),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 20.dp, top = 14.dp, bottom = 14.dp)
                    .height(24.dp)
                    .width(24.dp)
                    .scale(
                        scaleX = 1f,
                        scaleY = if (isDropped) -1f else 1f
                    )
                    .customClickable(
                        rippleEnabled = false,
                        onClick = onClickDropIcon
                    )
            )
        }
        HorizontalDivider(color = SolplyTheme.colors.gray300)
        AnimatedVisibility(
            visible = isDropped,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                content()
            }
        }
    }
}
