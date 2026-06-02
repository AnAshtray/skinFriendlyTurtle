package com.sunguard.domain.model

sealed class UvFetchException(message: String) : Exception(message) {
    class LocationUnavailable : UvFetchException("Location unavailable")
}
