package com.mashjulal.android.taplan.models.domain

data class ScheduledTask(
    private val id: Long,
    private val name: String,
    private val hours_per_week: Int,
    private val time_from: Long,
    private val time_to: Long
)