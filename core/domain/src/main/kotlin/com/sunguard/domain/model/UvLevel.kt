package com.sunguard.domain.model

enum class UvLevel(val label: String) {
    Low("Low"),
    Moderate("Moderate"),
    High("High"),
    VeryHigh("Very High"),
    Extreme("Extreme"),
    ;

    companion object {
        fun fromIndex(uvIndex: Float): UvLevel = when {
            uvIndex < 3f -> Low
            uvIndex < 6f -> Moderate
            uvIndex < 8f -> High
            uvIndex < 11f -> VeryHigh
            else -> Extreme
        }
    }
}
