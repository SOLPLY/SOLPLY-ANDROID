package com.teamsolply.solply.oauth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class GoogleLoginHelper(
    private val context: Context
) {

    companion object {
        const val TAG = "GoogleLogin"
        const val WEB_CLIENT_ID = com.teamsolply.solply.buildconfig.BuildConfig.GOOGLE_WEB_CLIENT_ID
        //const val SERVER_URL = ""
    }

    private val credentialManager: CredentialManager = CredentialManager.create(context)
    private val nonce = UUID.randomUUID().toString()
    private val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setServerClientId(WEB_CLIENT_ID) // 웹 클라이언트 ID
        .setFilterByAuthorizedAccounts(false)
//        .setNonce(nonce)
        .build()
    private val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    fun requestGoogleLogin(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )
                handleSignInResult(result, onSuccess, onFailure)
            } catch (e: GetCredentialException) {
                Log.e("Google Sign-in failed", " ${e.localizedMessage}")
            }
        }
    }

    private fun handleSignInResult(
        result: GetCredentialResponse,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
//                        Log.d(TAG, idToken) //토큰
//                        Log.d(TAG, googleIdTokenCredential.id) //이메일
//                        googleIdTokenCredential.displayName?.let { Log.d(TAG, it) } //이름
                        onSuccess(idToken) //성공 시 처리 함수, 서버 응답 후 실행, 여기서는 테스트를 위해 이곳에서 실행
                    } catch (e: GoogleIdTokenParsingException) {
//                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                } else {
//                    Log.e(TAG, "Unexpected type of credential")
                    onFailure("구글 로그인에 실패하였습니다. 다시 시도해주세요.")
                }
            }

            else -> {
//                Log.e(TAG, "Unexpected type of credential")
                onFailure("구글 로그인에 실패하였습니다. 다시 시도해주세요.")
            }
        }
    }
}
