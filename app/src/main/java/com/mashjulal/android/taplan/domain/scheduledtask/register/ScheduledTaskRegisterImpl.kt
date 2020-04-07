package com.mashjulal.android.taplan.domain.scheduledtask.register

import com.mashjulal.android.taplan.domain.scheduledtask.ScheduledTaskRepository
import com.mashjulal.android.taplan.domain.task.TaskRepository
import com.mashjulal.android.taplan.models.domain.CompletionStatus
import com.mashjulal.android.taplan.models.domain.Task
import com.mashjulal.android.taplan.presentation.utils.minutesToTimePair
import java.util.Calendar
import kotlin.math.roundToInt

class ScheduledTaskRegisterImpl(
    private val taskRepository: TaskRepository,
    private val scheduledTaskRepository: ScheduledTaskRepository
    ) : ScheduledTaskRegister {

    override suspend fun registerTasksForNextWeek(taskId: Long) {
        val task = taskRepository.getTask(taskId)
        val (hour, minutes) = minutesToTimePair(task.time_from.toInt())
        val minutesPerDay = ((task.hours_per_week * 60) / 7.0).roundToInt()

        val cal = Calendar.getInstance()

        val scheduledTasks = mutableListOf<Task>()
        var weekday = cal[Calendar.DAY_OF_WEEK]

        if (hour < cal[Calendar.HOUR] || (hour == cal[Calendar.HOUR] && minutes < cal[Calendar.MINUTE])) {
            ++weekday
        }
        cal[Calendar.HOUR] = hour
        cal[Calendar.MINUTE] = minutes
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        whenLoop@ while (true) {
            cal[Calendar.DAY_OF_WEEK] = weekday

            val scheduledStart = cal.timeInMillis
            val scheduledEnd = scheduledStart + (minutesPerDay * 60 * 1000)
            scheduledTasks.add(Task(0, taskId, scheduledStart, scheduledEnd, 0, 0, CompletionStatus.NOT_STARTED))
            when (weekday) {
                Calendar.SATURDAY -> {
                    weekday = Calendar.SUNDAY
                    ++cal[Calendar.WEEK_OF_YEAR]
                }
                Calendar.SUNDAY -> {
                    break@whenLoop
                }
                else -> ++weekday
            }
        }

        scheduledTaskRepository.insertTasks(scheduledTasks)
    }
}