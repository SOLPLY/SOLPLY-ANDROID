package com.teamsolply.solply.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MypageRoute(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    viewModel: MypageViewModel = hiltViewModel()
) {

}