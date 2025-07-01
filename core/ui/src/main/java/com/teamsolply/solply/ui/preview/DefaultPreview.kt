package com.teamsolply.solply.ui.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "phone",
    device = "spec:width=360dp,height=760dp,dpi=480",
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class DefaultPreview
