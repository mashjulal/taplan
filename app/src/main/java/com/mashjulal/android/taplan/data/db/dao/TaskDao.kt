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
    fun getAll(): List<TaskEntity>

    @Query("""
        SELECT * 
        FROM ${TaskTable.TABLE_NAME} 
        WHERE ${TaskTable.COLUMN_ID} = :id
        """)
    fun getById(id: Long): TaskEntity

    @Query("""
        SELECT * 
        FROM ${TaskTable.TABLE_NAME} 
        WHERE ${TaskTable.COLUMN_SCHEDULED_TASK_ID} = :scheduledTaskId
        """)
    fun getAllByScheduledTaskId(scheduledTaskId: Long): List<TaskEntity>

    @Insert
    fun insert(task: TaskEntity)

    @Update
    fun update(task: TaskEntity)

    @Delete
    fun delete(task: TaskEntity)

}