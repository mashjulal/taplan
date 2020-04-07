package com.mashjulal.android.taplan.data.repository

import com.mashjulal.android.taplan.data.db.dao.TaskDao
import com.mashjulal.android.taplan.domain.scheduledtask.ScheduledTaskRepository
import com.mashjulal.android.taplan.models.domain.CompletionStatus
import com.mashjulal.android.taplan.models.domain.Task

class ScheduledTaskRepositoryImpl(
    private val dao: TaskDao
) : ScheduledTaskRepository {

    override suspend fun getBetweenTimestamps(from: Long, to: Long) = dao.getBetweenTimestamps(from, to)
        .map { Task(it.id, it.scheduledTaskId, it.scheduledTimeStart, it.scheduledTimeEnd,
            it.actualTimeStart, it.actualTimeEnd, CompletionStatus.values()[it.completionStatus]) }

}