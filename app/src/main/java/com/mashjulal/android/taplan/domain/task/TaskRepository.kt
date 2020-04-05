package com.mashjulal.android.taplan.domain.task

import com.mashjulal.android.taplan.models.domain.ScheduledTask

interface TaskRepository {
    suspend fun getAllTasks(): List<ScheduledTask>
    suspend fun insertTask(task: ScheduledTask)
}