package com.teamsolply.solply

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.teamsolply.solply.buildconfig.BuildConfig.KAKAO_NATIVE_KEY
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SolplyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, KAKAO_NATIVE_KEY)
    }
}
