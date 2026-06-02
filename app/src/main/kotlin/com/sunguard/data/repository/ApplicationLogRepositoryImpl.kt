package com.sunguard.data.repository

import com.sunguard.data.local.ApplicationLogPreferences
import com.sunguard.data.local.toInstants
import com.sunguard.domain.model.ApplicationLogEntry
import com.sunguard.domain.repository.ApplicationLogRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationLogRepositoryImpl @Inject constructor(
    private val preferences: ApplicationLogPreferences,
) : ApplicationLogRepository {

    private var lastApplyInstant: Instant? = null
    private val initScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        initScope.launch { preferences.ensureCurrentDay() }
    }

    override fun observeTodayLog(): Flow<List<ApplicationLogEntry>> =
        preferences.observeTimestamps().map { millis ->
            millis.toInstants().sortedDescending().map { ApplicationLogEntry(it) }
        }

    override suspend fun logApplication(): Result<ApplicationLogEntry> {
        preferences.ensureCurrentDay()
        val now = Instant.now()
        val last = lastApplyInstant
        if (last != null && now.toEpochMilli() - last.toEpochMilli() < DEBOUNCE_MS) {
            return Result.failure(IllegalStateException("Apply debounced"))
        }
        preferences.append(now.toEpochMilli())
        lastApplyInstant = now
        return Result.success(ApplicationLogEntry(now))
    }

    override suspend fun clearTodayLog() {
        preferences.clearToday()
        lastApplyInstant = null
    }

    companion object {
        private const val DEBOUNCE_MS = 5_000L
    }
}
