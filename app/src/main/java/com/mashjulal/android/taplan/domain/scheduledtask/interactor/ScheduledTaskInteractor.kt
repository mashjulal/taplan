package com.mashjulal.android.taplan.domain.scheduledtask.interactor

import com.mashjulal.android.taplan.models.domain.Task

interface ScheduledTaskInteractor {

    suspend fun getCurrentWeekTasks(): List<Task>
}