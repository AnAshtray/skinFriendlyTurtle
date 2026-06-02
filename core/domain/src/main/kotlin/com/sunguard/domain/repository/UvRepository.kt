package com.sunguard.domain.repository

import com.sunguard.domain.model.UvSnapshot

interface UvRepository {
    suspend fun getCurrentUv(): Result<UvSnapshot>
}
