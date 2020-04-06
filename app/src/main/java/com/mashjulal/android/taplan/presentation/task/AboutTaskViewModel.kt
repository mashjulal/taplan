package com.mashjulal.android.taplan.presentation.task

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.data.android.ResourceExtractor
import com.mashjulal.android.taplan.domain.task.interactor.TaskInteractor
import com.mashjulal.android.taplan.models.domain.ScheduledTask
import com.mashjulal.android.taplan.presentation.utils.SingleLiveEvent
import com.mashjulal.android.taplan.presentation.utils.minutesToTimePair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException

class AboutTaskViewModel(
    private val resourceExtractor: ResourceExtractor,
    private val taskInteractor: TaskInteractor
): ViewModel() {

    val taskLiveData: MutableLiveData<ScheduledTask> = MutableLiveData()
    val editTaskSingleEvent: SingleLiveEvent<ScheduledTask> = SingleLiveEvent()

    fun title() = getTask().name

    fun hoursPerWeek() = getTask().hours_per_week.toString()

    fun hours(): String {
        val (fromHours, fromMinutes) = minutesToTimePair(getTask().time_from.toInt())
        val from = resourceExtractor.getString(R.string.time_hours_and_minutes_pattern, fromHours, fromMinutes)
        val (toHours, toMinutes) = minutesToTimePair(getTask().time_to.toInt())
        val to = resourceExtractor.getString(R.string.time_hours_and_minutes_pattern, toHours, toMinutes)
        return resourceExtractor.getString(R.string.period_pattern, from, to)
    }

    fun editTask() {
        editTaskSingleEvent.value = getTask()
    }

    fun updateTask() {
        viewModelScope.launch {
            var task: ScheduledTask? = null
            withContext(Dispatchers.IO) {
                task = taskInteractor.getTask(getTask().id)
            }
            taskLiveData.value = task
        }
    }

    private fun getTask() = taskLiveData.value ?: throw IllegalStateException()
}