package com.sunguard.domain.repository

import com.sunguard.domain.model.ApplicationLogEntry
import kotlinx.coroutines.flow.Flow

interface ApplicationLogRepository {
    fun observeTodayLog(): Flow<List<ApplicationLogEntry>>
    suspend fun logApplication(): Result<ApplicationLogEntry>
    suspend fun clearTodayLog()
}
