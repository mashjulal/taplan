package com.mashjulal.android.taplan.models.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mashjulal.android.taplan.data.db.DatabaseSchema.ScheduledTaskTable
import com.mashjulal.android.taplan.data.db.DatabaseSchema.TaskTable

@Entity(
    tableName = TaskTable.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = ScheduledTaskEntity::class,
            parentColumns = [ScheduledTaskTable.COLUMN_ID],
            childColumns = [TaskTable.COLUMN_SCHEDULED_TASK_ID],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TaskTable.COLUMN_ID)
    val id: Long,

    @ColumnInfo(name = TaskTable.COLUMN_SCHEDULED_TASK_ID)
    val scheduledTaskId: Long,

    @ColumnInfo(name = TaskTable.COLUMN_SCHEDULED_TIME_START)
    val scheduledTimeStart: Long,

    @ColumnInfo(name = TaskTable.COLUMN_SCHEDULED_TIME_END)
    val scheduledTimeEnd: Long,

    @ColumnInfo(name = TaskTable.COLUMN_ACTUAL_TIME_START)
    val actualTimeStart: Long,

    @ColumnInfo(name = TaskTable.COLUMN_ACTUAL_TIME_END)
    val actualTimeEnd: Long,

    @ColumnInfo(name = TaskTable.COLUMN_COMPLETION_STATUS)
    val completionStatus: Int
)