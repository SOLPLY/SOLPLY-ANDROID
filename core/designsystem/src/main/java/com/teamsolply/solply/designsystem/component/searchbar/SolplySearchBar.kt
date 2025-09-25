package com.teamsolply.solply.designsystem.component.searchbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun SolplySearchbar(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onImageClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = SolplyTheme.colors.gray300,
                shape = RoundedCornerShape(20.dp)
            )
            .background(color = SolplyTheme.colors.white)
            .padding(vertical = 14.dp, horizontal = 20.dp)
            .clickable {
                focusRequester.requestFocus()
                keyboardController?.show()
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            textStyle = SolplyTheme.typography.body16R.copy(color = SolplyTheme.colors.black),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = "찾는 공간을 입력하세요",
                        style = SolplyTheme.typography.body16R,
                        color = SolplyTheme.colors.gray500
                    )
                }
                innerTextField()
            },
            maxLines = 1,
            singleLine = true
        )
        Image(
            painter = painterResource(id = R.drawable.ic_setting), // ic_search 134 브랜치 들어오고 바꿔야함
            contentDescription = "Search Icon",
            modifier = Modifier
                .clickable {
                    onImageClick()
                    keyboardController?.hide()
                }
        )
    }
}

@Preview
@Composable
fun SearchBoxPreview() {
    SolplyTheme {
        SolplySearchbar(
            modifier = Modifier.fillMaxWidth(),
            query = "",
            onQueryChange = {},
            onImageClick = {}
        )
    }
}
