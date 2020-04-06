package com.mashjulal.android.taplan.models.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScheduledTask(
    val id: Long = 0,
    val name: String,
    val hours_per_week: Int,
    val time_from: Long,
    val time_to: Long
): Parcelable