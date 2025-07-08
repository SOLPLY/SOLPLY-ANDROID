package com.teamsolply.solply.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

// Grayscale
@Stable
val White = Color(0xFFFFFFFF)

@Stable
val Gray100 = Color(0xFFF7F5F6)

@Stable
val Gray200 = Color(0xFFEEEAEC)

@Stable
val Gray300 = Color(0xFFE3DDE0)

@Stable
val Gray400 = Color(0xFFD1CACD)

@Stable
val Gray500 = Color(0xFFB8AFB3)

@Stable
val Gray600 = Color(0xFFA89DA0)

@Stable
val Gray700 = Color(0xFF888183)

@Stable
val Gray800 = Color(0xFF696666)

@Stable
val Gray900 = Color(0xFF4F4B4B)

@Stable
val Black = Color(0xFF242424)

// Red
@Stable
val Red100 = Color(0xFFFFF2EB)

@Stable
val Red200 = Color(0xFFFFE1D3)

@Stable
val Red300 = Color(0xFFF7CFBF)

@Stable
val Red400 = Color(0xFFF1A699)

@Stable
val Red500 = Color(0xFFEA867B)

@Stable
val Red600 = Color(0xFFE46A61)

@Stable
val Red700 = Color(0xFFC94F44)

@Stable
val Red800 = Color(0xFFAA473E)

@Stable
val Red900 = Color(0xFF823E32)

// Yellow
@Stable
val Yellow100 = Color(0xFFFFF3DA)

@Stable
val Yellow200 = Color(0xFFFFE6B0)

@Stable
val Yellow300 = Color(0xFFF8D17C)

@Stable
val Yellow400 = Color(0xFFF9C45C)

@Stable
val Yellow500 = Color(0xFFF5B640)

@Stable
val Yellow600 = Color(0xFFD8982A)

@Stable
val Yellow700 = Color(0xFFA8772E)

@Stable
val Yellow800 = Color(0xFF66513F)

@Stable
val Yellow900 = Color(0xFF4E433B)

// Green
@Stable
val Green100 = Color(0xFFFAFBEC)

@Stable
val Green200 = Color(0xFFF8FCD9)

@Stable
val Green300 = Color(0xFFE9F0AF)

@Stable
val Green400 = Color(0xFFD3DD91)

@Stable
val Green500 = Color(0xFFACBD6F)

@Stable
val Green600 = Color(0xFF94AB65)

@Stable
val Green700 = Color(0xFF7F9360)

@Stable
val Green800 = Color(0xFF626F4F)

@Stable
val Green900 = Color(0xFF4E5940)

// Purple
@Stable
val Purple100 = Color(0xFFF6F6FF)

@Stable
val Purple200 = Color(0xFFE8E8FF)

@Stable
val Purple300 = Color(0xFFDDDDF5)

@Stable
val Purple400 = Color(0xFFCBCBE7)

@Stable
val Purple500 = Color(0xFFB4B2DB)

@Stable
val Purple600 = Color(0xFF9796C7)

@Stable
val Purple700 = Color(0xFF7B7AAD)

@Stable
val Purple800 = Color(0xFF595880)

@Stable
val Purple900 = Color(0xFF33324D)

@Stable
val Kakao = Color(0xFFFFD942)

@Stable
class SolplyColors(
    white: Color = White,
    gray100: Color = Gray100,
    gray200: Color = Gray200,
    gray300: Color = Gray300,
    gray400: Color = Gray400,
    gray500: Color = Gray500,
    gray600: Color = Gray600,
    gray700: Color = Gray700,
    gray800: Color = Gray800,
    gray900: Color = Gray900,
    black: Color = Black,

    red100: Color = Red100,
    red200: Color = Red200,
    red300: Color = Red300,
    red400: Color = Red400,
    red500: Color = Red500,
    red600: Color = Red600,
    red700: Color = Red700,
    red800: Color = Red800,
    red900: Color = Red900,

    yellow100: Color = Yellow100,
    yellow200: Color = Yellow200,
    yellow300: Color = Yellow300,
    yellow400: Color = Yellow400,
    yellow500: Color = Yellow500,
    yellow600: Color = Yellow600,
    yellow700: Color = Yellow700,
    yellow800: Color = Yellow800,
    yellow900: Color = Yellow900,

    green100: Color = Green100,
    green200: Color = Green200,
    green300: Color = Green300,
    green400: Color = Green400,
    green500: Color = Green500,
    green600: Color = Green600,
    green700: Color = Green700,
    green800: Color = Green800,
    green900: Color = Green900,

    purple100: Color = Purple100,
    purple200: Color = Purple200,
    purple300: Color = Purple300,
    purple400: Color = Purple400,
    purple500: Color = Purple500,
    purple600: Color = Purple600,
    purple700: Color = Purple700,
    purple800: Color = Purple800,
    purple900: Color = Purple900,

    kakao: Color = Kakao
) {
    var white by mutableStateOf(white)
        private set
    var gray100 by mutableStateOf(gray100)
        private set
    var gray200 by mutableStateOf(gray200)
        private set
    var gray300 by mutableStateOf(gray300)
        private set
    var gray400 by mutableStateOf(gray400)
        private set
    var gray500 by mutableStateOf(gray500)
        private set
    var gray600 by mutableStateOf(gray600)
        private set
    var gray700 by mutableStateOf(gray700)
        private set
    var gray800 by mutableStateOf(gray800)
        private set
    var gray900 by mutableStateOf(gray900)
        private set
    var black by mutableStateOf(black)
        private set

    var red100 by mutableStateOf(red100)
        private set
    var red200 by mutableStateOf(red200)
        private set
    var red300 by mutableStateOf(red300)
        private set
    var red400 by mutableStateOf(red400)
        private set
    var red500 by mutableStateOf(red500)
        private set
    var red600 by mutableStateOf(red600)
        private set
    var red700 by mutableStateOf(red700)
        private set
    var red800 by mutableStateOf(red800)
        private set
    var red900 by mutableStateOf(red900)
        private set

    var yellow100 by mutableStateOf(yellow100)
        private set
    var yellow200 by mutableStateOf(yellow200)
        private set
    var yellow300 by mutableStateOf(yellow300)
        private set
    var yellow400 by mutableStateOf(yellow400)
        private set
    var yellow500 by mutableStateOf(yellow500)
        private set
    var yellow600 by mutableStateOf(yellow600)
        private set
    var yellow700 by mutableStateOf(yellow700)
        private set
    var yellow800 by mutableStateOf(yellow800)
        private set
    var yellow900 by mutableStateOf(yellow900)
        private set

    var green100 by mutableStateOf(green100)
        private set
    var green200 by mutableStateOf(green200)
        private set
    var green300 by mutableStateOf(green300)
        private set
    var green400 by mutableStateOf(green400)
        private set
    var green500 by mutableStateOf(green500)
        private set
    var green600 by mutableStateOf(green600)
        private set
    var green700 by mutableStateOf(green700)
        private set
    var green800 by mutableStateOf(green800)
        private set
    var green900 by mutableStateOf(green900)
        private set

    var purple100 by mutableStateOf(purple100)
        private set
    var purple200 by mutableStateOf(purple200)
        private set
    var purple300 by mutableStateOf(purple300)
        private set
    var purple400 by mutableStateOf(purple400)
        private set
    var purple500 by mutableStateOf(purple500)
        private set
    var purple600 by mutableStateOf(purple600)
        private set
    var purple700 by mutableStateOf(purple700)
        private set
    var purple800 by mutableStateOf(purple800)
        private set
    var purple900 by mutableStateOf(purple900)
        private set

    var kakao by mutableStateOf(kakao)
        private set

    fun copy(): SolplyColors = SolplyColors(
        white = white,
        gray100 = gray100,
        gray200 = gray200,
        gray300 = gray300,
        gray400 = gray400,
        gray500 = gray500,
        gray600 = gray600,
        gray700 = gray700,
        gray800 = gray800,
        gray900 = gray900,
        black = black,

        red100 = red100,
        red200 = red200,
        red300 = red300,
        red400 = red400,
        red500 = red500,
        red600 = red600,
        red700 = red700,
        red800 = red800,
        red900 = red900,

        yellow100 = yellow100,
        yellow200 = yellow200,
        yellow300 = yellow300,
        yellow400 = yellow400,
        yellow500 = yellow500,
        yellow600 = yellow600,
        yellow700 = yellow700,
        yellow800 = yellow800,
        yellow900 = yellow900,

        green100 = green100,
        green200 = green200,
        green300 = green300,
        green400 = green400,
        green500 = green500,
        green600 = green600,
        green700 = green700,
        green800 = green800,
        green900 = green900,

        purple100 = purple100,
        purple200 = purple200,
        purple300 = purple300,
        purple400 = purple400,
        purple500 = purple500,
        purple600 = purple600,
        purple700 = purple700,
        purple800 = purple800,
        purple900 = purple900,
        kakao = kakao
    )

    fun update(other: SolplyColors) {
        white = other.white
        gray100 = other.gray100
        gray200 = other.gray200
        gray300 = other.gray300
        gray400 = other.gray400
        gray500 = other.gray500
        gray600 = other.gray600
        gray700 = other.gray700
        gray800 = other.gray800
        gray900 = other.gray900
        black = other.black

        red100 = other.red100
        red200 = other.red200
        red300 = other.red300
        red400 = other.red400
        red500 = other.red500
        red600 = other.red600
        red700 = other.red700
        red800 = other.red800
        red900 = other.red900

        yellow100 = other.yellow100
        yellow200 = other.yellow200
        yellow300 = other.yellow300
        yellow400 = other.yellow400
        yellow500 = other.yellow500
        yellow600 = other.yellow600
        yellow700 = other.yellow700
        yellow800 = other.yellow800
        yellow900 = other.yellow900

        green100 = other.green100
        green200 = other.green200
        green300 = other.green300
        green400 = other.green400
        green500 = other.green500
        green600 = other.green600
        green700 = other.green700
        green800 = other.green800
        green900 = other.green900

        purple100 = other.purple100
        purple200 = other.purple200
        purple300 = other.purple300
        purple400 = other.purple400
        purple500 = other.purple500
        purple600 = other.purple600
        purple700 = other.purple700
        purple800 = other.purple800
        purple900 = other.purple900

        kakao = other.kakao
    }
}

fun solplyColor(
    white: Color = White,
    gray100: Color = Gray100,
    gray200: Color = Gray200,
    gray300: Color = Gray300,
    gray400: Color = Gray400,
    gray500: Color = Gray500,
    gray600: Color = Gray600,
    gray700: Color = Gray700,
    gray800: Color = Gray800,
    gray900: Color = Gray900,
    black: Color = Black,

    red100: Color = Red100,
    red200: Color = Red200,
    red300: Color = Red300,
    red400: Color = Red400,
    red500: Color = Red500,
    red600: Color = Red600,
    red700: Color = Red700,
    red800: Color = Red800,
    red900: Color = Red900,

    yellow100: Color = Yellow100,
    yellow200: Color = Yellow200,
    yellow300: Color = Yellow300,
    yellow400: Color = Yellow400,
    yellow500: Color = Yellow500,
    yellow600: Color = Yellow600,
    yellow700: Color = Yellow700,
    yellow800: Color = Yellow800,
    yellow900: Color = Yellow900,

    green100: Color = Green100,
    green200: Color = Green200,
    green300: Color = Green300,
    green400: Color = Green400,
    green500: Color = Green500,
    green600: Color = Green600,
    green700: Color = Green700,
    green800: Color = Green800,
    green900: Color = Green900,

    purple100: Color = Purple100,
    purple200: Color = Purple200,
    purple300: Color = Purple300,
    purple400: Color = Purple400,
    purple500: Color = Purple500,
    purple600: Color = Purple600,
    purple700: Color = Purple700,
    purple800: Color = Purple800,
    purple900: Color = Purple900,

    kakao: Color = Kakao
): SolplyColors = SolplyColors(
    white = white,
    gray100 = gray100,
    gray200 = gray200,
    gray300 = gray300,
    gray400 = gray400,
    gray500 = gray500,
    gray600 = gray600,
    gray700 = gray700,
    gray800 = gray800,
    gray900 = gray900,
    black = black,

    red100 = red100,
    red200 = red200,
    red300 = red300,
    red400 = red400,
    red500 = red500,
    red600 = red600,
    red700 = red700,
    red800 = red800,
    red900 = red900,

    yellow100 = yellow100,
    yellow200 = yellow200,
    yellow300 = yellow300,
    yellow400 = yellow400,
    yellow500 = yellow500,
    yellow600 = yellow600,
    yellow700 = yellow700,
    yellow800 = yellow800,
    yellow900 = yellow900,

    green100 = green100,
    green200 = green200,
    green300 = green300,
    green400 = green400,
    green500 = green500,
    green600 = green600,
    green700 = green700,
    green800 = green800,
    green900 = green900,

    purple100 = purple100,
    purple200 = purple200,
    purple300 = purple300,
    purple400 = purple400,
    purple500 = purple500,
    purple600 = purple600,
    purple700 = purple700,
    purple800 = purple800,
    purple900 = purple900,

    kakao = kakao
)
