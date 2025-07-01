package com.teamsolply.solply.buildconfig.impl

import com.teamsolply.solply.buildconfig.BuildConfig.BASE_URL
import com.teamsolply.solply.buildconfig.BuildConfig.KAKAO_NATIVE_KEY
import com.teamsolply.solply.buildconfig.BuildConfig.NAVER_CLIENT_ID
import com.teamsolply.solply.common.buildconfig.BuildConfigFieldProvider
import com.teamsolply.solply.common.buildconfig.BuildConfigFields
import javax.inject.Inject

class BuildConfigFieldsProviderImpl @Inject constructor() : BuildConfigFieldProvider {
    override fun get(): BuildConfigFields =
        BuildConfigFields(
            baseUrl = BASE_URL,
            kakaoNativeKey = KAKAO_NATIVE_KEY,
            naverClientId = NAVER_CLIENT_ID,
            isDebug = true
        )
}
