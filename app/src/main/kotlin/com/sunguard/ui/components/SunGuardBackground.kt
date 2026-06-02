package com.sunguard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.sunguard.ui.theme.SunGuardColors

@Composable
fun SunGuardBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SunGuardColors.GradientTop,
                        SunGuardColors.GradientMid,
                        SunGuardColors.GradientBottom,
                    ),
                ),
            ),
    ) {
        content()
    }
}
