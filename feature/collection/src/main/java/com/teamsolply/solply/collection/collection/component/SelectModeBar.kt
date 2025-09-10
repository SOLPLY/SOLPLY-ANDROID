package com.teamsolply.solply.collection.collection.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.collection.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun SelectModeBar(
    selectMode: Boolean,
    onSelectButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit
) {
    val selectText =
        if (selectMode) stringResource(R.string.mypage_delete) else stringResource(R.string.mypage_select)
    val cancelText = if (selectMode) stringResource(R.string.mypage_cancel) else ""
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = cancelText,
            style = SolplyTheme.typography.button14R,
            color = SolplyTheme.colors.black,
            modifier = Modifier
                .padding(start = 12.dp)
                .then(
                    if (selectMode) {
                        Modifier.customClickable(
                            rippleEnabled = false
                        ) {
                            onCancelButtonClick()
                        }
                    } else {
                        Modifier
                    }
                )
        )
        Text(
            text = selectText,
            style = SolplyTheme.typography.button14R,
            color = SolplyTheme.colors.black,
            modifier = Modifier
                .padding(end = 12.dp)
                .then(
                    Modifier.customClickable(rippleEnabled = false) {
                        if (selectMode) {
                            onDeleteButtonClick()
                        } else {
                            onSelectButtonClick()
                        }
                    }
                )
        )
    }
}
