package com.sunguard.data.repository

import com.sunguard.data.local.UvCachePreferences
import com.sunguard.data.remote.OpenMeteoApi
import com.sunguard.domain.SpfCalculator
import com.sunguard.domain.model.UvFetchException
import com.sunguard.domain.model.UvLevel
import com.sunguard.domain.model.UvSnapshot
import com.sunguard.domain.repository.UvRepository
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class UvRepositoryImpl @Inject constructor(
    private val openMeteoApi: OpenMeteoApi,
    private val uvCache: UvCachePreferences,
    private val locationProvider: DeviceLocationProvider,
) : UvRepository {

    override suspend fun getCurrentUv(): Result<UvSnapshot> {
        val location = locationProvider.getLastKnownLocation()
        if (location != null) {
            return runCatching {
                val response = openMeteoApi.getForecast(
                    latitude = location.latitude,
                    longitude = location.longitude,
                )
                val uvIndex = response.current?.uvIndex?.toFloat()
                    ?: error("UV data unavailable")
                uvCache.write(uvIndex)
                snapshot(uvIndex, isCached = false)
            }.recoverCatching { loadCachedOrThrow(it) }
        }
        return runCatching {
            loadCachedOrThrow(UvFetchException.LocationUnavailable())
        }
    }

    private suspend fun loadCachedOrThrow(cause: Throwable): UvSnapshot {
        val cached = uvCache.read()
        if (cached != null && uvCache.isFresh(cached)) {
            return snapshot(cached.uvIndex, isCached = true, updatedAt = uvCache.toInstant(cached))
        }
        throw cause
    }

    private fun snapshot(
        uvIndex: Float,
        isCached: Boolean,
        updatedAt: Instant = Instant.now(),
    ): UvSnapshot {
        val level = UvLevel.fromIndex(uvIndex)
        return UvSnapshot(
            uvIndex = (uvIndex * 10).roundToInt() / 10f,
            recommendedSpf = SpfCalculator.recommendedSpf(uvIndex),
            recommendedSpfLabel = SpfCalculator.recommendedSpfLabel(uvIndex),
            level = level,
            updatedAt = updatedAt,
            isCached = isCached,
        )
    }
}
