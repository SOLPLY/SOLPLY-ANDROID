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
    black: Color
) {
    var white by mutableStateOf(white)
        private set

    var black by mutableStateOf(black)
        private set
}

fun SolplyColor(
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
        LocalSolplyColors provides colors,
        LocalSolplyTypography provides typography,
        content = content
    )
}

@Composable
fun SolplyTheme(content: @Composable () -> Unit) {
    val colors = SolplyColor()
    val typography = SolplyTypography()
    ProvideColorsAndTypography(colors, typography) {
        MaterialTheme(content = content)
    }
}