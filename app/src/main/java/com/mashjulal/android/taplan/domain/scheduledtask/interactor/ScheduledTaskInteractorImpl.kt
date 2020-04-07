package com.mashjulal.android.taplan.domain.scheduledtask.interactor

import com.mashjulal.android.taplan.domain.scheduledtask.ScheduledTaskRepository
import com.mashjulal.android.taplan.models.domain.Task
import java.util.Calendar

class ScheduledTaskInteractorImpl(
    private val repo: ScheduledTaskRepository
) : ScheduledTaskInteractor {

    override suspend fun getCurrentWeekTasks(): List<Task> {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        cal[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        val from = cal.timeInMillis

        cal[Calendar.DAY_OF_WEEK] = Calendar.SUNDAY
        cal[Calendar.HOUR] = 23
        cal[Calendar.MINUTE] = 59
        cal[Calendar.SECOND] = 59
        cal[Calendar.MILLISECOND] = 999
        ++cal[Calendar.WEEK_OF_YEAR]
        val to = cal.timeInMillis

        return repo.getBetweenTimestamps(from, to)
    }

}