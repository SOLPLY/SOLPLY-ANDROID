package com.teamsolply.solply.oauth.presentation

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OauthRoute(
    paddingValues: PaddingValues,
    viewModel: OauthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                OauthSideEffect.StartKakaoLogin -> startKakaoLogin(
                    context = context,
                    onSuccess = { accessToken, refreshToken ->
                        Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
                        Log.d(
                            "asdasdasd",
                            "accessToken: ${accessToken}\n refreshToken: $refreshToken"
                        )
                    },
                    onFailure = { error ->
                        Log.d("asdasdasd", error.toString())
                    }
                )
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
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Oauth",
            modifier = Modifier.customClickable(
                onClick = kakaoLoginClick
            )
        )
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
                    Toast.makeText(context, "로그인 취소", Toast.LENGTH_SHORT).show()
                    return@loginWithKakaoTalk
                }
                UserApiClient.instance.loginWithKakaoAccount(context) { accountToken, accountError ->
                    if (accountError != null) {
                        onFailure(accountError)
                        Toast.makeText(
                            context,
                            "로그인 실패: ${accountError.message}",
                            Toast.LENGTH_SHORT
                        ).show()
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
            Log.d("KAKAO_LOGIN", "loginWithKakaoAccount called. token=$token, error=$error")
            if (error != null) {
                onFailure(error)
                Toast.makeText(context, "로그인 실패: ${error.message}", Toast.LENGTH_SHORT).show()
                return@loginWithKakaoAccount
            }
            if (token != null) {
                onSuccess(token.accessToken, token.refreshToken)
            }
        }
    }
}
