package com.sunguard.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = SunGuardColors.SunOrange,
    onPrimary = SunGuardColors.SunOrange,
    background = SunGuardColors.GradientTop,
    onBackground = SunGuardColors.SunOrange,
    surface = SunGuardColors.GradientTop,
    onSurface = SunGuardColors.SunOrange,
)

@Composable
fun SunGuardTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = SunGuardTypography,
        content = content,
    )
}
