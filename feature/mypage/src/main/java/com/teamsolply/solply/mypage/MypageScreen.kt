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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.component.EmptyPlaceContainer
import com.teamsolply.solply.mypage.component.MypageSettingItem
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun MypageRoute(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    navigateToProfile: () -> Unit,
    viewModel: MypageViewModel = hiltViewModel()
) {
    MypageScreen(
        onBackButtonClick = navigateToBack,
        onProfileEditClick = navigateToProfile,
        modifier = Modifier.padding(paddingValues),
    )
}

@Composable
fun MypageScreen(
    onBackButtonClick: () -> Unit,
    onProfileEditClick: () -> Unit,
    modifier: Modifier = Modifier
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
                    .padding(start = 16.dp, bottom = 16.dp, top = 11.dp)
                    .customClickable(rippleEnabled = false) { onBackButtonClick() }
            )
        }
        Image(
            painter = painterResource(R.drawable.img_basic_profile),
            contentDescription = "기본 프로필",
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "닉네임",
            color = SolplyTheme.colors.black,
            style = SolplyTheme.typography.display20Sb
        )
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .customClickable(
                    rippleEnabled = false,
                    onClick = onProfileEditClick
                )
        ) {
            Text(
                text = "프로필 수정",
                color = SolplyTheme.colors.gray600,
                style = SolplyTheme.typography.button14M
            )
        }
        Spacer(modifier = Modifier.height(44.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SolplyTheme.colors.white),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "내가 등록한 장소",
                    color = SolplyTheme.colors.black,
                    style = SolplyTheme.typography.body16M
                )
            }
            EmptyPlaceContainer()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SolplyTheme.colors.white),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 20.dp, end = 16.dp, bottom = 12.dp)
            ) {
                Text(
                    text = "계정 설정",
                    color = SolplyTheme.colors.black,
                    style = SolplyTheme.typography.body16M
                )
            }
            MypageSettingItem(
                text = "고객센터",
                onClick = { /* TODO */ },
                isBorderEnabled = true
            )
            MypageSettingItem(
                text = "로그인 정보",
                onClick = { /* TODO */ },
                info = "카카오 로그인",
                isBorderEnabled = true
            )
            MypageSettingItem(
                text = "앱 버전",
                onClick = { /* TODO */ },
                info = "v 1.0.0",
                isBorderEnabled = true
            )
            MypageSettingItem(
                text = "로그아웃",
                onClick = { /* TODO */ },
                isBorderEnabled = true
            )
            MypageSettingItem(
                text = "탈퇴하기",
                onClick = { /* TODO */ },
                isBorderEnabled = false
            )
        }
        Spacer(
            modifier = Modifier.weight(36f)
        )
    }
}

@Composable
@Preview
private fun MypageScreenPreview() {
    SolplyTheme {
        MypageScreen(
            onBackButtonClick = {},
            onProfileEditClick = {}
        )
    }
}
