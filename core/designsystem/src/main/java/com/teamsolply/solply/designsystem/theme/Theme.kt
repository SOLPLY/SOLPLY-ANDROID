package com.teamsolply.solply.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

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
    val provideColors = remember { colors.copy() }
    provideColors.update(colors)
    val provideTypography = remember { typography.copy() }
    provideTypography.update(typography)

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
