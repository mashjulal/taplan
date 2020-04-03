package com.mashjulal.android.taplan.presentation.scheduledtasks

import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.android.ResourceExtractor
import com.mashjulal.android.taplan.models.domain.CompletionStatus
import com.mashjulal.android.taplan.models.domain.ScheduledTask
import com.mashjulal.android.taplan.models.domain.Task
import com.mashjulal.android.taplan.models.presentation.SectionHeaderViewModel
import com.mashjulal.android.taplan.models.presentation.TaskViewModel
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel
import java.text.SimpleDateFormat
import java.util.*

class ScheduledTasksViewModel(
    private val resourceExtractor: ResourceExtractor
) : ViewModel() {

    val itemsLiveData: MutableLiveData<List<ItemViewModel>> = MutableLiveData()

    init {
        val scheduledTasks = listOf(
            ScheduledTask(0, "Task 1", 40, 0, 0),
            ScheduledTask(1, "Task 2", 40, 0, 0),
            ScheduledTask(2, "Task 3", 40, 0, 0)
        )

        val today = java.util.Calendar.getInstance()
        today.set(java.util.Calendar.HOUR, 0)
        today.set(java.util.Calendar.MINUTE, 0)
        today.set(java.util.Calendar.SECOND, 0)

        val tasks = mutableListOf(
            Task(0, 0, today.timeInMillis, today.timeInMillis+1000*60*60, 0, 0, CompletionStatus.NOT_STARTED),
            Task(1, 1, today.timeInMillis+1000*60*60, today.timeInMillis+1000*60*60*2, 0, 0, CompletionStatus.NOT_STARTED),
            Task(2, 2, today.timeInMillis+1000*60*60*2, today.timeInMillis+1000*60*60*3, 0, 0, CompletionStatus.NOT_STARTED),
            Task(3, 1, today.timeInMillis+1000*60*60*3, today.timeInMillis+1000*60*60*4, 0, 0, CompletionStatus.NOT_STARTED),
            Task(4, 2, today.timeInMillis+1000*60*60*4, today.timeInMillis+1000*60*60*5, 0, 0, CompletionStatus.NOT_STARTED),
            Task(5, 0, today.timeInMillis+1000*60*60*5, today.timeInMillis+1000*60*60*6, 0, 0, CompletionStatus.NOT_STARTED),
            Task(6, 2, today.timeInMillis+1000*60*60*6, today.timeInMillis+1000*60*60*7, 0, 0, CompletionStatus.NOT_STARTED)
        )

        prepareViewModels(tasks, scheduledTasks)
    }

    private fun prepareViewModels(tasks: List<Task>, scheduledTasks: List<ScheduledTask>) {
        val modelList = mutableListOf<ItemViewModel>()

        modelList.add(SectionHeaderViewModel(resourceExtractor.getString(R.string.today)))
        val dtFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)
        tasks.forEach {
            val schTask = scheduledTasks.find { st -> st.id == it.scheduledTaskId } ?: throw IllegalArgumentException()
            val title = schTask.name
            val from = dtFormat.format(Date(it.scheduled_time_start))
            val to = dtFormat.format(Date(it.scheduled_time_end))
            val period = resourceExtractor.getString(R.string.period_pattern, from, to)

            modelList.add(TaskViewModel(title, period))
        }

        itemsLiveData.value = modelList
    }

}
