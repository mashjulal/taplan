package com.mashjulal.android.taplan.domain.scheduledtask.register

interface ScheduledTaskRegister {
    suspend fun registerTasksForNextWeek(taskId: Long)
}