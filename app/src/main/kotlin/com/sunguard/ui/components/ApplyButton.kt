package com.sunguard.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.sunguard.R
import com.sunguard.ui.theme.ApplyButtonTextStyle
import com.sunguard.ui.theme.SunGuardColors

@Composable
fun ApplyButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale = if (pressed && enabled) 0.97f else 1f
    val fillAlpha = if (pressed && enabled) 0.15f else 0f

    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth(0.5f)
            .height(52.dp)
            .scale(scale)
            .semantics {
                contentDescription = "Apply sunscreen"
            },
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(1.5.dp, SunGuardColors.SunOrange),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = SunGuardColors.SunOrange.copy(alpha = fillAlpha),
            contentColor = SunGuardColors.SunOrange,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = SunGuardColors.SunOrange.copy(alpha = 0.6f),
        ),
        interactionSource = interactionSource,
    ) {
        Text(
            text = stringResource(R.string.apply_button),
            style = ApplyButtonTextStyle,
        )
    }
}
