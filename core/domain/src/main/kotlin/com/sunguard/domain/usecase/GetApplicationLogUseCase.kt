package com.sunguard.domain.usecase

import com.sunguard.domain.model.ApplicationLogEntry
import com.sunguard.domain.repository.ApplicationLogRepository
import kotlinx.coroutines.flow.Flow

class GetApplicationLogUseCase(
    private val applicationLogRepository: ApplicationLogRepository,
) {
    operator fun invoke(): Flow<List<ApplicationLogEntry>> =
        applicationLogRepository.observeTodayLog()
}
