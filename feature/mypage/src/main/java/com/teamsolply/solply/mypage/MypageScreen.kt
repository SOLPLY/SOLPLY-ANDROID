package com.teamsolply.solply.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun MypageRoute(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    viewModel: MypageViewModel = hiltViewModel()
) {
    MypageScreen(
        onBackButtonClick = navigateToBack
    )
}

@Composable
fun MypageScreen(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = SolplyTheme.colors.gray100),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_back),
                contentDescription = "back",
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .customClickable(rippleEnabled = false) { onBackButtonClick() }
            )
            Spacer(
                modifier = Modifier.width(12.dp)
            )
        }
        Image(
            painter = painterResource(R.drawable.img_basic_profile),
            contentDescription = "기본 프로필",
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
        )
    }
}

@Composable
@Preview
private fun MypageScreenPreview() {
    SolplyTheme {
        MypageScreen(
            onBackButtonClick = {}
        )
    }
}