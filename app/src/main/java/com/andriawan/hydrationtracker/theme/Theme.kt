package com.andriawan.hydrationtracker.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariantColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    onBackground = OnBackgroundColor,
    onPrimary = OnPrimaryColor,
    surface = BackgroundColor,
    onSurface = OnBackgroundColor
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariantColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    onBackground = OnBackgroundColor,
    onPrimary = OnPrimaryColor,
    surface = BackgroundColor,
    onSurface = OnBackgroundColor
)

@Composable
fun HydrationTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(colors.background, darkIcons = true)
    systemUiController.setNavigationBarColor(colors.primaryVariant, darkIcons = true)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}