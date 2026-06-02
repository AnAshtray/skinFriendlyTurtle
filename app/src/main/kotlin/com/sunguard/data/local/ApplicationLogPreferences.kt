package com.sunguard.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

private fun Long.isToday(): Boolean {
    val entryDate = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    return entryDate == LocalDate.now(ZoneId.systemDefault())
}

private val Context.applicationLogDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "application_log",
)

@Singleton
class ApplicationLogPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val dataStore = context.applicationLogDataStore

    fun observeTimestamps(): Flow<List<Long>> =
        dataStore.data.map { prefs ->
            val dayKey = dayKey()
            val raw = prefs[stringPreferencesKey(dayKey)] ?: ""
            if (raw.isEmpty()) emptyList()
            else raw.split(",").mapNotNull { it.toLongOrNull() }.filter { it.isToday() }
        }

    suspend fun append(timestampMillis: Long) {
        dataStore.edit { prefs ->
            val key = stringPreferencesKey(dayKey())
            val existing = prefs[key]?.split(",")?.filter { it.isNotBlank() }?.mapNotNull { it.toLongOrNull() }
                ?: emptyList()
            val updated = (existing + timestampMillis).takeLast(MAX_ENTRIES)
            prefs[key] = updated.joinToString(",")
            prefs[LAST_APPLY_KEY] = timestampMillis
        }
    }

    suspend fun clearToday() {
        dataStore.edit { prefs ->
            prefs.remove(stringPreferencesKey(dayKey()))
            prefs.remove(LAST_APPLY_KEY)
        }
    }

    suspend fun ensureCurrentDay() {
        dataStore.edit { prefs ->
            val storedDay = prefs[CURRENT_DAY_KEY]
            val today = dayKey()
            if (storedDay != today) {
                prefs.remove(stringPreferencesKey(storedDay ?: ""))
                prefs.remove(LAST_APPLY_KEY)
                prefs[CURRENT_DAY_KEY] = today
            }
        }
    }

    private fun dayKey(): String {
        val today = LocalDate.now(ZoneId.systemDefault())
        return "log_${today.year}_${today.monthValue}_${today.dayOfMonth}"
    }

    companion object {
        private const val MAX_ENTRIES = 50
        private val CURRENT_DAY_KEY = stringPreferencesKey("current_day")
        private val LAST_APPLY_KEY = longPreferencesKey("last_apply_millis")
    }
}

fun List<Long>.toInstants(): List<Instant> = map { Instant.ofEpochMilli(it) }
