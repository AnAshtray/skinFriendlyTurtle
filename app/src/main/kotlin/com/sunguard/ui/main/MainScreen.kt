package com.sunguard.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import android.widget.Toast
import com.sunguard.R
import com.sunguard.ui.components.ApplicationLogSection
import com.sunguard.ui.components.ApplyButton
import com.sunguard.ui.components.ApplyLoadingOverlay
import com.sunguard.ui.components.HeroSunIcon
import com.sunguard.ui.components.StatsRow
import com.sunguard.ui.components.SunGuardBackground
import com.sunguard.ui.theme.FooterTaglineTextStyle

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.onToastShown()
        }
    }

    SunGuardBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            HeroSunIcon(contentDescription = stringResource(R.string.hero_content_description))
            Spacer(modifier = Modifier.height(32.dp))
            ApplyButton(
                onClick = viewModel::onApplyClicked,
                enabled = uiState.isApplyEnabled,
            )
            Spacer(modifier = Modifier.height(32.dp))
            StatsRow(
                uvIndexText = uiState.uvIndexText,
                spfText = uiState.spfText,
                lastAppliedText = uiState.lastAppliedText,
            )
            Spacer(modifier = Modifier.height(32.dp))
            ApplicationLogSection(entries = uiState.logLines)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.footer_tagline),
                style = FooterTaglineTextStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, top = 16.dp),
            )
        }

        if (uiState.showApplyLoading) {
            ApplyLoadingOverlay()
        }
    }
}
