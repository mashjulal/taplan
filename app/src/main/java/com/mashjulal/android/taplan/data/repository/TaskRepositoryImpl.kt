package com.mashjulal.android.taplan.data.repository

import com.mashjulal.android.taplan.data.db.dao.ScheduledTaskDao
import com.mashjulal.android.taplan.domain.task.TaskRepository
import com.mashjulal.android.taplan.models.data.db.ScheduledTaskEntity
import com.mashjulal.android.taplan.models.domain.ScheduledTask

class TaskRepositoryImpl(
    private val dao: ScheduledTaskDao
) : TaskRepository {

    override suspend fun getAllTasks(): List<ScheduledTask> = dao.getAll()
        .map { ScheduledTask(it.id, it.name, it.hoursPerWeek, it.timeFrom, it.timeTo) }

    override suspend fun insertTask(task: ScheduledTask) {
        dao.insert(ScheduledTaskEntity(task.id, task.name, task.hours_per_week, task.time_from, task.time_to))
    }

    override suspend fun getTask(id: Long): ScheduledTask {
        val task = dao.getById(id)
        return ScheduledTask(task.id, task.name, task.hoursPerWeek, task.timeFrom, task.timeTo)
    }

    override suspend fun updateTask(task: ScheduledTask) {
        dao.update(ScheduledTaskEntity(task.id, task.name, task.hours_per_week, task.time_from, task.time_to))
    }

}