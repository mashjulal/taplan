package com.mashjulal.android.taplan.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mashjulal.android.taplan.data.db.dao.ScheduledTaskDao
import com.mashjulal.android.taplan.data.db.dao.TaskDao
import com.mashjulal.android.taplan.models.data.db.ScheduledTaskEntity
import com.mashjulal.android.taplan.models.data.db.TaskEntity
import org.koin.android.ext.koin.androidContext

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

    companion object {
        fun build(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java,
            DatabaseSchema.DATABASE_NAME)
            .build()
    }
}