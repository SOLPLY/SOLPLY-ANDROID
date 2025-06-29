package com.teamsolply.solply.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val PretendardMedium = FontFamily.Serif
val PretendardSemiBold = FontFamily.Serif
val PretendardRegular = FontFamily.Serif

@Stable
class SolplyTypography internal constructor(
    val display24Sb: TextStyle,
    val display20Sb: TextStyle,
    val display16Sb: TextStyle,
    val display12R: TextStyle,

    val title18Sb: TextStyle,
    val title15M: TextStyle,
    val title14M: TextStyle,

    val body16M: TextStyle,
    val body16R: TextStyle,
    val body14M: TextStyle,
    val body14R: TextStyle,

    val head16M: TextStyle,
    val head15Sb: TextStyle,
    val head15M: TextStyle,

    val button16M: TextStyle,
    val button14M: TextStyle,
    val button14R: TextStyle,
    val button12M: TextStyle,

    val caption14M: TextStyle,
    val caption14R: TextStyle,
    val caption12M: TextStyle,
    val caption12R: TextStyle,
    val caption10M: TextStyle,
    val caption10R: TextStyle
)

fun SolplyTypography(): SolplyTypography {
    return SolplyTypography(
        display24Sb = TextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = (24 * 1.5).sp
        ),
        display20Sb = TextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = (20 * 1.5).sp
        ),
        display16Sb = TextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = (16 * 1.3).sp
        ),
        display12R = TextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = (12 * 1.3).sp
        ),
        title18Sb = TextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = (18 * 1.3).sp
        ),
        title15M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = (15 * 1.3).sp
        ),
        title14M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = (14 * 1.3).sp
        ),
        body16M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = (16 * 1.5).sp
        ),
        body16R = TextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = (16 * 1.5).sp
        ),
        body14M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = (14 * 1.5).sp
        ),
        body14R = TextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = (14 * 1.5).sp
        ),
        head16M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = (16 * 1.3).sp
        ),
        head15Sb = TextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            lineHeight = (15 * 1.3).sp
        ),
        head15M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = (15 * 1.3).sp
        ),
        button16M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = (16 * 1.3).sp
        ),
        button14M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = (14 * 1.3).sp
        ),
        button14R = TextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = (14 * 1.3).sp
        ),
        button12M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = (12 * 1.3).sp
        ),
        caption14M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = (14 * 1.5).sp
        ),
        caption14R = TextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = (14 * 1.5).sp
        ),
        caption12M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = (12 * 1.5).sp
        ),
        caption12R = TextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = (12 * 1.5).sp
        ),
        caption10M = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = (10 * 1.5).sp
        ),
        caption10R = TextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            lineHeight = (10 * 1.5).sp
        )
    )
}
