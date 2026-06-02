package com.sunguard.domain.model

import java.time.Instant

data class UvSnapshot(
    val uvIndex: Float,
    val recommendedSpf: Int,
    val recommendedSpfLabel: String,
    val level: UvLevel,
    val updatedAt: Instant,
    val isCached: Boolean = false,
)
