package com.mashjulal.android.taplan.data.db.dao

import androidx.room.*
import com.mashjulal.android.taplan.data.db.DatabaseSchema.ScheduledTaskTable
import com.mashjulal.android.taplan.models.data.db.ScheduledTaskEntity


@Dao
interface ScheduledTaskDao {

    @Query("""
        SELECT * 
        FROM ${ScheduledTaskTable.TABLE_NAME}
        """)
    fun getAll(): List<ScheduledTaskEntity>

    @Query("""
        SELECT * 
        FROM ${ScheduledTaskTable.TABLE_NAME} 
        WHERE ${ScheduledTaskTable.COLUMN_ID} = :id
        """)
    fun getById(id: Long): ScheduledTaskEntity

    @Insert
    fun insert(task: ScheduledTaskEntity)

    @Update
    fun update(task: ScheduledTaskEntity)

    @Delete
    fun delete(task: ScheduledTaskEntity)

}