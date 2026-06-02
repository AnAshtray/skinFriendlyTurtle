package com.sunguard.domain

import kotlin.math.ceil

/**
 * PRD §6.2: SPF tiers from UV index.
 */
object SpfCalculator {
    fun recommendedSpf(uvIndex: Float): Int = when {
        uvIndex <= 2f -> 15
        uvIndex <= 5f -> 30
        uvIndex <= 7f -> 50
        else -> 50
    }

    fun recommendedSpfLabel(uvIndex: Float): String = when {
        uvIndex <= 2f -> "15-30"
        uvIndex <= 5f -> "30-50"
        uvIndex <= 7f -> "50+"
        else -> "50+"
    }

    fun rawSpf(uvIndex: Float): Double = ceil(uvIndex * 2.5).toDouble()
}
