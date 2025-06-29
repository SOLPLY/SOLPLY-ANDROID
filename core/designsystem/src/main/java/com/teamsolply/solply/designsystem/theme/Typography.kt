package com.teamsolply.solply.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.teamsolply.solply.designsystem.R

val PretendardRegular = FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))

@Stable
class SolplyTypography internal constructor(
    display24Sb: TextStyle,
    display20Sb: TextStyle,
    display16Sb: TextStyle,
    display12R: TextStyle,

    title18Sb: TextStyle,
    title15M: TextStyle,
    title14M: TextStyle,

    body16M: TextStyle,
    body16R: TextStyle,
    body14M: TextStyle,
    body14R: TextStyle,

    head16M: TextStyle,
    head15Sb: TextStyle,
    head15M: TextStyle,

    button16M: TextStyle,
    button14M: TextStyle,
    button14R: TextStyle,
    button12M: TextStyle,

    caption14M: TextStyle,
    caption14R: TextStyle,
    caption12M: TextStyle,
    caption12R: TextStyle,
    caption10M: TextStyle,
    caption10R: TextStyle
) {
    var display24Sb: TextStyle by mutableStateOf(display24Sb)
        private set
    var display20Sb: TextStyle by mutableStateOf(display20Sb)
        private set
    var display16Sb: TextStyle by mutableStateOf(display16Sb)
        private set
    var display12R: TextStyle by mutableStateOf(display12R)
        private set

    var title18Sb: TextStyle by mutableStateOf(title18Sb)
        private set
    var title15M: TextStyle by mutableStateOf(title15M)
        private set
    var title14M: TextStyle by mutableStateOf(title14M)
        private set

    var body16M: TextStyle by mutableStateOf(body16M)
        private set
    var body16R: TextStyle by mutableStateOf(body16R)
        private set
    var body14M: TextStyle by mutableStateOf(body14M)
        private set
    var body14R: TextStyle by mutableStateOf(body14R)
        private set

    var head16M: TextStyle by mutableStateOf(head16M)
        private set
    var head15Sb: TextStyle by mutableStateOf(head15Sb)
        private set
    var head15M: TextStyle by mutableStateOf(head15M)
        private set

    var button16M: TextStyle by mutableStateOf(button16M)
        private set
    var button14M: TextStyle by mutableStateOf(button14M)
        private set
    var button14R: TextStyle by mutableStateOf(button14R)
        private set
    var button12M: TextStyle by mutableStateOf(button12M)
        private set

    var caption14M: TextStyle by mutableStateOf(caption14M)
        private set
    var caption14R: TextStyle by mutableStateOf(caption14R)
        private set
    var caption12M: TextStyle by mutableStateOf(caption12M)
        private set
    var caption12R: TextStyle by mutableStateOf(caption12R)
        private set
    var caption10M: TextStyle by mutableStateOf(caption10M)
        private set
    var caption10R: TextStyle by mutableStateOf(caption10R)
        private set


    fun copy(
        display24Sb: TextStyle = this.display24Sb,
        display20Sb: TextStyle = this.display20Sb,
        display16Sb: TextStyle = this.display16Sb,
        display12R: TextStyle = this.display12R,

        title18Sb: TextStyle = this.title18Sb,
        title15M: TextStyle = this.title15M,
        title14M: TextStyle = this.title14M,

        body16M: TextStyle = this.body16M,
        body16R: TextStyle = this.body16R,
        body14M: TextStyle = this.body14M,
        body14R: TextStyle = this.body14R,

        head16M: TextStyle = this.head16M,
        head15Sb: TextStyle = this.head15Sb,
        head15M: TextStyle = this.head15M,

        button16M: TextStyle = this.button16M,
        button14M: TextStyle = this.button14M,
        button14R: TextStyle = this.button14R,
        button12M: TextStyle = this.button12M,

        caption14M: TextStyle = this.caption14M,
        caption14R: TextStyle = this.caption14R,
        caption12M: TextStyle = this.caption12M,
        caption12R: TextStyle = this.caption12R,
        caption10M: TextStyle = this.caption10M,
        caption10R: TextStyle = this.caption10R
    ): SolplyTypography = SolplyTypography(
        display24Sb = display24Sb,
        display20Sb = display20Sb,
        display16Sb = display16Sb,
        display12R = display12R,

        title18Sb = title18Sb,
        title15M = title15M,
        title14M = title14M,

        body16M = body16M,
        body16R = body16R,
        body14M = body14M,
        body14R = body14R,

        head16M = head16M,
        head15Sb = head15Sb,
        head15M = head15M,

        button16M = button16M,
        button14M = button14M,
        button14R = button14R,
        button12M = button12M,

        caption14M = caption14M,
        caption14R = caption14R,
        caption12M = caption12M,
        caption12R = caption12R,
        caption10M = caption10M,
        caption10R = caption10R
    )

    fun update(other: SolplyTypography) {
        this.display24Sb = other.display24Sb
        this.display20Sb = other.display20Sb
        this.display16Sb = other.display16Sb
        this.display12R = other.display12R

        this.title18Sb = other.title18Sb
        this.title15M = other.title15M
        this.title14M = other.title14M

        this.body16M = other.body16M
        this.body16R = other.body16R
        this.body14M = other.body14M
        this.body14R = other.body14R

        this.head16M = other.head16M
        this.head15Sb = other.head15Sb
        this.head15M = other.head15M

        this.button16M = other.button16M
        this.button14M = other.button14M
        this.button14R = other.button14R
        this.button12M = other.button12M

        this.caption14M = other.caption14M
        this.caption14R = other.caption14R
        this.caption12M = other.caption12M
        this.caption12R = other.caption12R
        this.caption10M = other.caption10M
        this.caption10R = other.caption10R
    }
}

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

