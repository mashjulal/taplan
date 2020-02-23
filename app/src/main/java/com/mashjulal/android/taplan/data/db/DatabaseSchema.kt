package com.mashjulal.android.taplan.data.db

object DatabaseSchema {
    const val DATABASE_NAME = "app-database"

    object ScheduledTaskTable {
        const val TABLE_NAME = "ScheduledTask"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_HOURS_PER_WEEK = "hours_per_week"
        const val COLUMN_TIME_FROM = "time_from"
        const val COLUMN_TIME_TO = "time_to"
    }

    object TaskTable {
        const val TABLE_NAME = "Task"

        const val COLUMN_ID = "id"
        const val COLUMN_SCHEDULED_TASK_ID = "scheduled_task_id"
        const val COLUMN_SCHEDULED_TIME_START = "scheduled_time_start"
        const val COLUMN_SCHEDULED_TIME_END = "scheduled_time_end"
        const val COLUMN_ACTUAL_TIME_START = "actual_time_start"
        const val COLUMN_ACTUAL_TIME_END = "actual_time_end"
        const val COLUMN_COMPLETION_STATUS = "completion_status"
    }
}