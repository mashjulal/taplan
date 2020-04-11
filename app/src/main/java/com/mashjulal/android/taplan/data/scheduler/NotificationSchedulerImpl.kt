package com.mashjulal.android.taplan.data.scheduler

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy.REPLACE
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mashjulal.android.taplan.data.android.worker.scheduler.TaskSchedulerWorker
import com.mashjulal.android.taplan.domain.WorkScheduler
import java.util.*
import java.util.concurrent.TimeUnit

private const val WORK_NAME = "Taplan notification scheduler work"

class NotificationSchedulerImpl(
    private val context: Context
): WorkScheduler {

    private fun getDelayInMinutes(): Long {
        val nextDay = Calendar.getInstance().also {
            ++it[Calendar.DAY_OF_WEEK]
            it[Calendar.HOUR_OF_DAY] = 1
            it[Calendar.MINUTE] = 0
            it[Calendar.SECOND] = 0
            it[Calendar.MILLISECOND] = 0
        }
        val delay = nextDay.timeInMillis - Calendar.getInstance().timeInMillis

        return TimeUnit.MILLISECONDS.toMinutes(delay)
    }

    override suspend fun schedule() {
        val request = PeriodicWorkRequestBuilder<TaskSchedulerWorker>(1, TimeUnit.DAYS, 10, TimeUnit.MINUTES)
            .setInitialDelay(getDelayInMinutes(), TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(WORK_NAME, REPLACE, request)
    }
}