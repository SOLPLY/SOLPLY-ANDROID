package com.teamsolply.solply.collection.collection.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.collection.R
import com.teamsolply.solply.designsystem.component.dialog.SolplyConfirmDialog
import com.teamsolply.solply.designsystem.component.topbar.SolplyTopBar
import com.teamsolply.solply.designsystem.theme.SolplyTheme

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
        modifier
            .fillMaxSize()
            .background(color = SolplyTheme.colors.white),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SolplyTopBar(
            barText = town,
            onBackButtonClick = { onBackButtonClick() }
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            content = content
        )
    }
}

@Preview
@Composable
private fun CollectionScreenPreview() {
    SolplyTheme {
        CollectionScreen(
            town = "",
            onBackButtonClick = {},
            onDialogConfirmClick = {},
            onDialogDismissClick = {},
            content = {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SelectModeBar(
                        selectMode = false,
                        onSelectButtonClick = { },
                        onDeleteButtonClick = { },
                        onCancelButtonClick = { }
                    )
                }
            },
            dialogState = false
        )
    }
}
