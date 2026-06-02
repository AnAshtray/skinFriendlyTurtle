package com.sunguard.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunguard.domain.model.ApplicationLogEntry
import com.sunguard.domain.model.UvFetchException
import com.sunguard.domain.model.UvLevel
import com.sunguard.domain.usecase.ApplySunscreenUseCase
import com.sunguard.domain.usecase.GetApplicationLogUseCase
import com.sunguard.domain.usecase.GetUvSnapshotUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val applySunscreenUseCase: ApplySunscreenUseCase,
    private val getApplicationLogUseCase: GetApplicationLogUseCase,
    private val getUvSnapshotUseCase: GetUvSnapshotUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    init {
        observeLog()
        refreshUv()
    }

    fun onApplyClicked() {
        viewModelScope.launch {
            _uiState.update { it.copy(isApplyEnabled = false, showApplyLoading = true) }
            applySunscreenUseCase()
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            showApplyLoading = false,
                            lastAppliedText = formatTime(it.appliedAt),
                        )
                    }
                    scheduleApplyReEnable()
                }
                .onFailure {
                    _uiState.update { state ->
                        state.copy(showApplyLoading = false, isApplyEnabled = true)
                    }
                }
        }
    }

    fun refreshUv() {
        viewModelScope.launch {
            getUvSnapshotUseCase()
                .onSuccess { snapshot ->
                    val uvText = buildString {
                        append(snapshot.uvIndex.toString())
                        if (snapshot.isCached) append(" *")
                    }
                    _uiState.update {
                        it.copy(
                            uvIndexText = uvText,
                            spfText = buildSpfText(snapshot.recommendedSpfLabel, snapshot.level),
                            isUvCached = snapshot.isCached,
                        )
                    }
                }
                .onFailure { throwable ->
                    val toast = when (throwable) {
                        is UvFetchException.LocationUnavailable -> "please enable location services"
                        else -> "Unable to fetch UV data"
                    }
                    _uiState.update { it.copy(toastMessage = toast) }
                }
        }
    }

    fun onToastShown() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    private fun observeLog() {
        viewModelScope.launch {
            getApplicationLogUseCase().collect { entries ->
                _uiState.update { state ->
                    state.copy(
                        logLines = entries.map { formatLogLine(it) },
                        lastAppliedText = entries.firstOrNull()?.let { formatTime(it.appliedAt) }
                            ?: "Not yet",
                    )
                }
            }
        }
    }

    private fun formatLogLine(entry: ApplicationLogEntry): String =
        "${formatTime(entry.appliedAt)} — Applied ☀️"

    private fun formatTime(instant: Instant): String =
        timeFormatter.format(instant.atZone(ZoneId.systemDefault()))

    private fun buildSpfText(spfLabel: String, level: UvLevel): String {
        return if (level == UvLevel.Extreme) {
            "$spfLabel • Limit outdoor activities"
        } else {
            spfLabel
        }
    }

    private fun scheduleApplyReEnable() {
        viewModelScope.launch {
            delay(APPLY_DEBOUNCE_MS)
            _uiState.update { it.copy(isApplyEnabled = true) }
        }
    }

    companion object {
        private const val APPLY_DEBOUNCE_MS = 5_000L
    }
}
