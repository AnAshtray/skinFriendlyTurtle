package com.sunguard.domain.usecase

import com.sunguard.domain.model.UvSnapshot
import com.sunguard.domain.repository.UvRepository

class GetUvSnapshotUseCase(
    private val uvRepository: UvRepository,
) {
    suspend operator fun invoke(): Result<UvSnapshot> = uvRepository.getCurrentUv()
}
