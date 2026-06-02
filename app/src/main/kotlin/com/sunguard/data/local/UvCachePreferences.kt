package com.sunguard.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

private val Context.uvCacheDataStore: DataStore<Preferences> by preferencesDataStore(name = "uv_cache")

data class CachedUv(
    val uvIndex: Float,
    val updatedAtMillis: Long,
)

@Singleton
class UvCachePreferences @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val dataStore = context.uvCacheDataStore

    suspend fun read(): CachedUv? {
        return dataStore.data.map { prefs ->
            val uv = prefs[UV_KEY] ?: return@map null
            val updated = prefs[UPDATED_KEY] ?: return@map null
            CachedUv(uv, updated)
        }.first()
    }

    suspend fun write(uvIndex: Float) {
        dataStore.edit { prefs ->
            prefs[UV_KEY] = uvIndex
            prefs[UPDATED_KEY] = System.currentTimeMillis()
        }
    }

    fun isFresh(cached: CachedUv, maxAgeMillis: Long = ONE_HOUR_MS): Boolean =
        System.currentTimeMillis() - cached.updatedAtMillis <= maxAgeMillis

    fun toInstant(cached: CachedUv): Instant = Instant.ofEpochMilli(cached.updatedAtMillis)

    companion object {
        private val UV_KEY = floatPreferencesKey("uv_index")
        private val UPDATED_KEY = longPreferencesKey("updated_at")
        private const val ONE_HOUR_MS = 60 * 60 * 1000L
    }
}
