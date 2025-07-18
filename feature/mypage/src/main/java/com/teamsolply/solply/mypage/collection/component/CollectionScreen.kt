package com.teamsolply.solply.mypage.collection.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.dialog.SolplyConfirmDialog
import com.teamsolply.solply.designsystem.component.topbar.SolplyTopBar
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.R
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun CollectionScreen(
    town: String,
    onBackButtonClick: () -> Unit,
    onDialogConfirmClick: () -> Unit,
    onDialogDismissClick: () -> Unit,
    dialogState: Boolean,
    content: LazyGridScope.() -> Unit,
    modifier: Modifier = Modifier
) {

    if (dialogState) {
        SolplyConfirmDialog(
            text = "선택한 장소를 삭제할까요?",
            confirmButtonText = stringResource(R.string.mypage_delete),
            dismissButtonText = stringResource(R.string.mypage_cancel),
            onClickConfirm = onDialogConfirmClick,
            onClickDismiss = onDialogDismissClick
        )
    }
    Column(
        modifier.fillMaxSize()
            .background(color = SolplyTheme.colors.white),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SolplyTopBar(
            barText = town,
            onBackButtonClick = { onBackButtonClick() }
        )
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp),
            content = content
        )
    }
}
