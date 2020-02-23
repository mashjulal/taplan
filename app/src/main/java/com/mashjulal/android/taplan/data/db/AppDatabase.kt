package com.mashjulal.android.taplan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mashjulal.android.taplan.data.db.dao.ScheduledTaskDao
import com.mashjulal.android.taplan.data.db.dao.TaskDao
import com.mashjulal.android.taplan.models.data.db.ScheduledTaskEntity
import com.mashjulal.android.taplan.models.data.db.TaskEntity

@Database(
    entities = [
        ScheduledTaskEntity::class,
        TaskEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getScheduledTaskDao(): ScheduledTaskDao
    abstract fun getTaskDao(): TaskDao
}