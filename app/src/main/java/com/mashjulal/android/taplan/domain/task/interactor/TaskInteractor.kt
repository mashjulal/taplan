package com.mashjulal.android.taplan.domain.task.interactor

import com.mashjulal.android.taplan.models.domain.ScheduledTask

interface TaskInteractor {

    suspend fun getAllTasks(): List<ScheduledTask>
    suspend fun insertTask(task: ScheduledTask)
    suspend fun getTask(id: Long): ScheduledTask
    suspend fun updateTask(task: ScheduledTask)
}