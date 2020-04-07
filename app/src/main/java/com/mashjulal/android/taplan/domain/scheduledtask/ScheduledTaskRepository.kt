package com.mashjulal.android.taplan.domain.scheduledtask

import com.mashjulal.android.taplan.models.domain.Task

interface ScheduledTaskRepository {
    suspend fun getBetweenTimestamps(from: Long, to: Long): List<Task>
}