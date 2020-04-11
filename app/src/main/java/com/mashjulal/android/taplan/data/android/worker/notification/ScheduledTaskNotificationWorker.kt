package com.mashjulal.android.taplan.data.android.worker.notification

import android.content.Context
import androidx.work.*
import com.mashjulal.android.taplan.data.android.worker.WorkerFactoryCreator
import com.mashjulal.android.taplan.domain.scheduledtask.ScheduledTaskRepository
import com.mashjulal.android.taplan.domain.task.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit

private const val WORK_NAME = "Taplan notification worker"

class ScheduledTaskNotificationWorker(
    private val taskRepository: TaskRepository,
    private val scheduledTaskRepository: ScheduledTaskRepository,
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val fiveMinutes = 5 * 60 * 1000
        val from = Calendar.getInstance().timeInMillis
        val to = Calendar.getInstance().also {
            ++it[Calendar.DAY_OF_MONTH]
            it[Calendar.HOUR_OF_DAY] = 0
            it[Calendar.MINUTE] = 0
            it[Calendar.SECOND] = 0
            it[Calendar.MILLISECOND] = 0
        }.timeInMillis

        val scheduledTasks = scheduledTaskRepository.getBetweenTimestamps(from, to)
        val tasks = taskRepository.getAllTasks()

        var title: String
        var data: Data
        val requests = mutableListOf<OneTimeWorkRequest>()
        var request: OneTimeWorkRequest
        scheduledTasks.forEach {task ->
            title = tasks.first { it.id == task.scheduledTaskId }.name
            data = Data.Builder()
                .putString("task title", title)
                .putLong("task id", task.id)
                .build()

            request = OneTimeWorkRequestBuilder<ShowNotificationWorker>()
                .setInputData(data)
                .setInitialDelay(task.actual_time_start-fiveMinutes, TimeUnit.MILLISECONDS)
                .setConstraints(Constraints.Builder().setTriggerContentMaxDelay(1, TimeUnit.MILLISECONDS).build())
                .build()
            requests.add(request)
        }

        WorkManager.getInstance(applicationContext).enqueue(requests)
        Result.success()
    }

    companion object {
        class FactoryCreator(
            private val taskRepository: TaskRepository,
            private val scheduledTaskRepository: ScheduledTaskRepository
        ): WorkerFactoryCreator {
            override fun create(context: Context, workerParameters: WorkerParameters) =
                ScheduledTaskNotificationWorker(
                    taskRepository,
                    scheduledTaskRepository,
                    context,
                    workerParameters
                )
        }
    }
}