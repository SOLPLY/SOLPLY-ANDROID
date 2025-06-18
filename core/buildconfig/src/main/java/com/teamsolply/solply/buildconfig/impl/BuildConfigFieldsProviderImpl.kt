package com.teamsolply.solply.buildconfig.impl

import com.teamsolply.solply.buildconfig.BuildConfig
import com.teamsolply.solply.common.buildconfig.BuildConfigFieldProvider
import com.teamsolply.solply.common.buildconfig.BuildConfigFields
import javax.inject.Inject

class BuildConfigFieldsProviderImpl @Inject constructor() : BuildConfigFieldProvider {
    override fun get(): BuildConfigFields =
        BuildConfigFields(
            baseUrl = BuildConfig.BASE_URL,
            isDebug = true
        )
}