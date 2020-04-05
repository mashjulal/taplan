package com.mashjulal.android.taplan.domain.task.interactor

import com.mashjulal.android.taplan.domain.task.TaskRepository
import com.mashjulal.android.taplan.models.domain.ScheduledTask

class TaskInteractorImpl(
    private val repo: TaskRepository
) : TaskInteractor {

    override suspend fun getAllTasks(): List<ScheduledTask> = repo.getAllTasks()

    override suspend fun insertTask(task: ScheduledTask) {
        repo.insertTask(task)
    }
}