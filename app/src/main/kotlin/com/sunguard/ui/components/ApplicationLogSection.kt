package com.sunguard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunguard.R
import com.sunguard.ui.theme.LogEntryTextStyle
import com.sunguard.ui.theme.SectionHeaderScriptTextStyle

@Composable
fun ApplicationLogSection(
    entries: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(0.7f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.application_log_header),
            style = SectionHeaderScriptTextStyle,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
        )
        if (entries.isEmpty()) {
            Text(
                text = stringResource(R.string.log_empty),
                style = LogEntryTextStyle.copy(fontSize = 11.sp, lineHeight = 16.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp),
            )
        } else {
            entries.forEach { line ->
                Text(
                    text = line,
                    style = LogEntryTextStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.height(28.dp),
                )
            }
        }
    }
}
