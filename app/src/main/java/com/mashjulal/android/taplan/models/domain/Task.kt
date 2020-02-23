package com.mashjulal.android.taplan.models.domain

data class Task(
    private val id: Long,
    private val scheduledTaskId: Long,
    private val scheduled_time_start: Long,
    private val scheduled_time_end: Long,
    private val actual_time_start: Long,
    private val actual_time_end: Long,
    private val completion_status: CompletionStatus
)