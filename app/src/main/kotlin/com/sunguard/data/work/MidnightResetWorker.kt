package com.sunguard.data.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sunguard.domain.repository.ApplicationLogRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MidnightResetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val applicationLogRepository: ApplicationLogRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        applicationLogRepository.clearTodayLog()
        return Result.success()
    }
}
