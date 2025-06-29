package com.teamsolply.solply.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
class SolplyColors(
    white: Color,
    black: Color,

    val gray100: Color = Gray100,
    val gray200: Color = Gray200,
    val gray300: Color = Gray300,
    val gray400: Color = Gray400,
    val gray500: Color = Gray500,
    val gray600: Color = Gray600,
    val gray700: Color = Gray700,
    val gray800: Color = Gray800,
    val gray900: Color = Gray900,

    val red100: Color = Red100,
    val red200: Color = Red200,
    val red300: Color = Red300,
    val red400: Color = Red400,
    val red500: Color = Red500,
    val red600: Color = Red600,
    val red700: Color = Red700,
    val red800: Color = Red800,
    val red900: Color = Red900,

    val yellow100: Color = Yellow100,
    val yellow200: Color = Yellow200,
    val yellow300: Color = Yellow300,
    val yellow400: Color = Yellow400,
    val yellow500: Color = Yellow500,
    val yellow600: Color = Yellow600,
    val yellow700: Color = Yellow700,
    val yellow800: Color = Yellow800,
    val yellow900: Color = Yellow900,

    val green100: Color = Green100,
    val green200: Color = Green200,
    val green300: Color = Green300,
    val green400: Color = Green400,
    val green500: Color = Green500,
    val green600: Color = Green600,
    val green700: Color = Green700,
    val green800: Color = Green800,
    val green900: Color = Green900,

    val purple100: Color = Purple100,
    val purple200: Color = Purple200,
    val purple300: Color = Purple300,
    val purple400: Color = Purple400,
    val purple500: Color = Purple500,
    val purple600: Color = Purple600,
    val purple700: Color = Purple700,
    val purple800: Color = Purple800,
    val purple900: Color = Purple900
) {
    var white by mutableStateOf(white)
        private set

    var black by mutableStateOf(black)
        private set
}

fun solplyColor(
    white: Color = White,
    black: Color = Black
) = SolplyColors(
    white = white,
    black = black
)

private val LocalSolplyColors =
    staticCompositionLocalOf<SolplyColors> { error("provide none color") }

private val LocalSolplyTypography =
    staticCompositionLocalOf<SolplyTypography> { error("provide none typography") }

object SolplyTheme {
    val colors: SolplyColors
        @Composable
        @ReadOnlyComposable
        get() = LocalSolplyColors.current

    val typography: SolplyTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalSolplyTypography.current
}

@Composable
fun ProvideColorsAndTypography(
    colors: SolplyColors,
    typography: SolplyTypography,
    content: @Composable () -> Unit
) {
    val provideColors = remember { colors }
    val provideTypography = remember { typography }

    CompositionLocalProvider(
        LocalSolplyColors provides provideColors,
        LocalSolplyTypography provides provideTypography,
        content = content
    )
}

@Composable
fun SolplyTheme(content: @Composable () -> Unit) {
    val colors = solplyColor()
    val typography = SolplyTypography()
    ProvideColorsAndTypography(colors, typography) {
        MaterialTheme(content = content)
    }
}
