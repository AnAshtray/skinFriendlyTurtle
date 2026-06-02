package com.sunguard.ui.main

data class MainUiState(
    val uvIndexText: String = "—",
    val spfText: String = "—",
    val lastAppliedText: String = "Not yet",
    val logLines: List<String> = emptyList(),
    val isApplyEnabled: Boolean = true,
    val showApplyLoading: Boolean = false,
    val isUvCached: Boolean = false,
    val toastMessage: String? = null,
)
