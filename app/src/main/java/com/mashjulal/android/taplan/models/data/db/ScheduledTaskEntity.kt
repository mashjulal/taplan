package com.mashjulal.android.taplan.models.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mashjulal.android.taplan.data.db.DatabaseSchema.ScheduledTaskTable

@Entity(tableName = ScheduledTaskTable.TABLE_NAME)
data class ScheduledTaskEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ScheduledTaskTable.COLUMN_ID)
    val id: Long,

    @ColumnInfo(name = ScheduledTaskTable.COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = ScheduledTaskTable.COLUMN_HOURS_PER_WEEK)
    val hoursPerWeek: Int,

    @ColumnInfo(name = ScheduledTaskTable.COLUMN_TIME_FROM)
    val timeFrom: Long,

    @ColumnInfo(name = ScheduledTaskTable.COLUMN_TIME_TO)
    val timeTo: Long
)