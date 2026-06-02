package com.sunguard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sunguard.R
import com.sunguard.ui.theme.StatsLabelTextStyle
import com.sunguard.ui.theme.StatsValueTextStyle
import com.sunguard.ui.theme.SunGuardColors

@Composable
fun StatsRow(
    uvIndexText: String,
    spfText: String,
    lastAppliedText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        StatColumn(
            icon = Icons.Outlined.WbSunny,
            label = stringResource(R.string.stat_uv_index),
            value = uvIndexText,
        )
        StatColumn(
            icon = Icons.Outlined.Shield,
            label = stringResource(R.string.stat_spf),
            value = spfText,
        )
        StatColumn(
            icon = Icons.Outlined.Schedule,
            label = stringResource(R.string.stat_last_applied),
            value = lastAppliedText,
        )
    }
}

@Composable
private fun StatColumn(
    icon: ImageVector,
    label: String,
    value: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = SunGuardColors.SunOrange,
            modifier = Modifier.size(28.dp),
        )
        Text(text = value, style = StatsValueTextStyle, textAlign = TextAlign.Center)
        Text(text = label, style = StatsLabelTextStyle, textAlign = TextAlign.Center)
    }
}
