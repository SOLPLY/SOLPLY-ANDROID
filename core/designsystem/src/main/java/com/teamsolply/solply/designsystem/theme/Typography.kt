package com.teamsolply.solply.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

val PretendardMedium = FontFamily.Serif

@Stable
class SolplyTypography internal constructor(
    bodyMedium17: TextStyle
) {
    var bodyMedium17: TextStyle by mutableStateOf(bodyMedium17)
        private set
}

fun SolplyTypography(): SolplyTypography {
    return SolplyTypography(
        bodyMedium17 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 17.sp,
            lineHeight = 24.sp
        )
    )
}
