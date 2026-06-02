package com.sunguard.domain.usecase

import com.sunguard.domain.model.ApplicationLogEntry
import com.sunguard.domain.repository.ApplicationLogRepository

class ApplySunscreenUseCase(
    private val applicationLogRepository: ApplicationLogRepository,
) {
    suspend operator fun invoke(): Result<ApplicationLogEntry> =
        applicationLogRepository.logApplication()
}
