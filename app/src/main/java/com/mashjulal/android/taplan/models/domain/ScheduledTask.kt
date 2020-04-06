package com.mashjulal.android.taplan.models.domain

data class ScheduledTask(
    val id: Long = 0,
    val name: String,
    val hours_per_week: Int,
    val time_from: Long,
    val time_to: Long
)