package com.mashjulal.android.taplan.domain.task.interactor

import com.mashjulal.android.taplan.domain.scheduledtask.register.ScheduledTaskRegister
import com.mashjulal.android.taplan.domain.task.TaskRepository
import com.mashjulal.android.taplan.models.domain.ScheduledTask

class TaskInteractorImpl(
    private val repo: TaskRepository,
    private val scheduledTaskRegister: ScheduledTaskRegister
) : TaskInteractor {

    override suspend fun getAllTasks(): List<ScheduledTask> = repo.getAllTasks()

    override suspend fun insertTask(task: ScheduledTask) {
        val taskId = repo.insertTask(task)
        scheduledTaskRegister.registerTasksForNextWeek(taskId)
    }

    override suspend fun getTask(id: Long): ScheduledTask = repo.getTask(id)
    override suspend fun updateTask(task: ScheduledTask) {
        repo.updateTask(task)
    }
}