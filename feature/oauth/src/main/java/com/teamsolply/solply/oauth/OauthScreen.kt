package com.teamsolply.solply.oauth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.oauth.R.string.kakao_login
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OauthRoute(
    paddingValues: PaddingValues,
    navigateToOnBoarding: () -> Unit,
    navigateToPlace: () -> Unit,
    viewModel: OauthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                OauthSideEffect.StartKakaoLogin -> startKakaoLogin(
                    context = context,
                    onSuccess = { accessToken, _ ->
                        viewModel.sendIntent(
                            OauthIntent.KakaoLoginSuccess(
                                provider = "KAKAO",
                                accessToken = accessToken
                            )
                        )
                    }
                )

                OauthSideEffect.NavigateToOnBoarding -> navigateToOnBoarding()
                OauthSideEffect.NavigateToPlace -> navigateToPlace()
            }
        }
    }

    OauthScreen(
        kakaoLoginClick = { viewModel.sendIntent(OauthIntent.KakaoLoginClick) }
    )
}

@Composable
fun OauthScreen(
    kakaoLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(115.dp))
        Image(
            painter = painterResource(R.drawable.ic_logo_full_vector),
            contentDescription = "app_logo",
            modifier = Modifier
                .padding(start = 40.dp)
        )

        Image(
            painter = painterResource(R.drawable.ic_lettering_vector),
            contentDescription = "app_logo",
            modifier = Modifier
                .padding(start = 40.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(173.dp)
                .padding(8.dp)
        ) {
            Text(
                text = "혼자만의 시간을\n더 쉽게, 더 즐겁게!",
                color = SolplyTheme.colors.gray800,
                style = SolplyTheme.typography.display20Sb,
                modifier = modifier
                    .padding(start = 40.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(start = 20.dp, end = 20.dp)
                .background(
                    color = SolplyTheme.colors.kakao,
                    shape = RoundedCornerShape(12.dp)
                )
                .customClickable(
                    rippleEnabled = false
                ) {
                    kakaoLoginClick()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_kakao_logo),
                contentDescription = "kakao_logo",
                modifier = Modifier
                    .padding(start = 16.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
            )
            Text(
                text = stringResource(kakao_login),
                style = SolplyTheme.typography.button16M,
                color = SolplyTheme.colors.gray900
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

fun startKakaoLogin(
    context: Context,
    onSuccess: (accessToken: String, refreshToken: String?) -> Unit = { _, _ -> },
    onFailure: (Throwable) -> Unit = {}
) {
    val activity = context as? Activity ?: return

    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(activity) { token, error ->
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                UserApiClient.instance.loginWithKakaoAccount(context) { accountToken, accountError ->
                    if (accountError != null) {
                        onFailure(accountError)
                        return@loginWithKakaoAccount
                    }
                    if (accountToken != null) {
                        onSuccess(accountToken.accessToken, accountToken.refreshToken)
                    }
                }
            } else if (token != null) {
                onSuccess(token.accessToken, token.refreshToken)
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                onFailure(error)
                return@loginWithKakaoAccount
            }
            if (token != null) {
                onSuccess(token.accessToken, token.refreshToken)
            }
        }
    }
}
