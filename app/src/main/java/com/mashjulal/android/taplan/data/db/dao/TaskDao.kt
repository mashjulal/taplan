package com.mashjulal.android.taplan.data.db.dao

import androidx.room.*
import com.mashjulal.android.taplan.data.db.DatabaseSchema.TaskTable
import com.mashjulal.android.taplan.models.data.db.TaskEntity

@Dao
interface TaskDao {

    @Query("""
        SELECT * 
        FROM ${TaskTable.TABLE_NAME}
        """)
    suspend fun getAll(): List<TaskEntity>

    @Query("""
        SELECT * 
        FROM ${TaskTable.TABLE_NAME}
        WHERE ${TaskTable.COLUMN_SCHEDULED_TIME_START} >= :from AND ${TaskTable.COLUMN_SCHEDULED_TIME_END} <= :to
        ORDER BY ${TaskTable.COLUMN_SCHEDULED_TIME_START}, ${TaskTable.COLUMN_SCHEDULED_TIME_END}
        """)
    suspend fun getBetweenTimestamps(from: Long, to: Long): List<TaskEntity>

    @Query("""
        SELECT * 
        FROM ${TaskTable.TABLE_NAME} 
        WHERE ${TaskTable.COLUMN_ID} = :id
        """)
    suspend fun getById(id: Long): TaskEntity

    @Query("""
        SELECT * 
        FROM ${TaskTable.TABLE_NAME} 
        WHERE ${TaskTable.COLUMN_SCHEDULED_TASK_ID} = :scheduledTaskId
        """)
    suspend fun getAllByScheduledTaskId(scheduledTaskId: Long): List<TaskEntity>

    @Insert
    suspend fun insert(task: TaskEntity)

    @Insert
    suspend fun insert(tasks: List<TaskEntity>)

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

}