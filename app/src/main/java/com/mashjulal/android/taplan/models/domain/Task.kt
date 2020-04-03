package com.mashjulal.android.taplan.models.domain

data class Task(
    val id: Long,
    val scheduledTaskId: Long,
    val scheduled_time_start: Long,
    val scheduled_time_end: Long,
    val actual_time_start: Long,
    val actual_time_end: Long,
    val completion_status: CompletionStatus
)