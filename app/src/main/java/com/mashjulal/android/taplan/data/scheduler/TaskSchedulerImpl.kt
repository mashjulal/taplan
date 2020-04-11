package com.mashjulal.android.taplan.data.scheduler

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy.REPLACE
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mashjulal.android.taplan.data.android.TaskSchedulerWorker
import com.mashjulal.android.taplan.domain.TaskScheduler
import java.util.*
import java.util.concurrent.TimeUnit

private const val WORK_NAME = "Taplan work"

class TaskSchedulerImpl(
    private val context: Context
): TaskScheduler {

    private fun getDelayInMinutes(): Long {
        val nextWeekdayFirstDay = Calendar.getInstance().also {
            it[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
            ++it[Calendar.WEEK_OF_YEAR]
            it[Calendar.HOUR_OF_DAY] = 1
            it[Calendar.MINUTE] = 0
        }
        val delay = nextWeekdayFirstDay.timeInMillis - Calendar.getInstance().timeInMillis

        return TimeUnit.MILLISECONDS.toMinutes(delay)
    }

    override suspend fun schedule() {
        val request = PeriodicWorkRequestBuilder<TaskSchedulerWorker>(7, TimeUnit.DAYS, 30, TimeUnit.MINUTES)
            .setInitialDelay(getDelayInMinutes(), TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(WORK_NAME, REPLACE, request)
    }
}