package com.mashjulal.android.taplan.data.android

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mashjulal.android.taplan.data.android.worker.WorkerFactoryCreator
import com.mashjulal.android.taplan.domain.TaskScheduler
import com.mashjulal.android.taplan.domain.scheduledtask.register.ScheduledTaskRegister
import com.mashjulal.android.taplan.domain.task.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskSchedulerWorker(
    private val taskRepository: TaskRepository,
    private val taskRegister: ScheduledTaskRegister,
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {


    override suspend fun doWork() = withContext(Dispatchers.IO) {
        taskRepository.getAllTasks().forEach {
            taskRegister.registerTasksForNextWeek(it.id)
        }
        Result.success()
    }

    companion object {
        class FactoryCreator(
            private val taskRepository: TaskRepository,
            private val taskRegister: ScheduledTaskRegister
        ): WorkerFactoryCreator {
            override fun create(context: Context, workerParameters: WorkerParameters) =
                TaskSchedulerWorker(taskRepository, taskRegister, context, workerParameters)
        }
    }
}