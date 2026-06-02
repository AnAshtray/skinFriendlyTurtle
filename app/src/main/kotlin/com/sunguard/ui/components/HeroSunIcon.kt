package com.sunguard.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.sunguard.ui.theme.SunGuardColors
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HeroSunIcon(
    modifier: Modifier = Modifier,
    contentDescription: String,
) {
    val transition = rememberInfiniteTransition(label = "sun-rotate")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 24_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "rotation",
    )

    Canvas(
        modifier = modifier
            .size(120.dp)
            .semantics { this.contentDescription = contentDescription },
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val petalColor = SunGuardColors.SunOrange
        val coreRadius = size.minDimension * 0.18f
        val petalLength = size.minDimension * 0.28f
        val petalCount = 6

        drawCircle(color = petalColor, radius = coreRadius, center = center)

        for (index in 0 until petalCount) {
            val angle = Math.toRadians((rotation + index * 60f).toDouble())
            val start = Offset(
                center.x + cos(angle).toFloat() * coreRadius,
                center.y + sin(angle).toFloat() * coreRadius,
            )
            val end = Offset(
                center.x + cos(angle).toFloat() * (coreRadius + petalLength),
                center.y + sin(angle).toFloat() * (coreRadius + petalLength),
            )
            drawLine(
                color = petalColor,
                start = start,
                end = end,
                strokeWidth = size.minDimension * 0.09f,
                cap = StrokeCap.Round,
            )
        }

        drawCircle(
            color = Color.Transparent,
            radius = coreRadius + petalLength * 0.35f,
            center = center,
            style = Stroke(width = size.minDimension * 0.02f),
        )
    }
}
