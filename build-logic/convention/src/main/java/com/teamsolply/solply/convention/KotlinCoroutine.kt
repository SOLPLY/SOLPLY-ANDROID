package com.teamsolply.solply.convention

import com.android.build.api.dsl.CommonExtension
import com.teamsolply.solply.convention.extension.getBundle
import com.teamsolply.solply.convention.extension.implementation
import com.teamsolply.solply.convention.extension.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinCoroutine(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            implementation(libs.getBundle("coroutine"))
        }
    }
}