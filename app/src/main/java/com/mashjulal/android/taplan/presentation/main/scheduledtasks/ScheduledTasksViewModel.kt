package com.mashjulal.android.taplan.presentation.main.scheduledtasks

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

        val today = Calendar.getInstance()

        val tasks = mutableListOf(
            Task(0, 0, today.timeInMillis, today.timeInMillis+1000*60*60, 0, 0, CompletionStatus.NOT_STARTED),
            Task(1, 1, today.timeInMillis+1000*60*60, today.timeInMillis+1000*60*60*2, 0, 0, CompletionStatus.NOT_STARTED),
            Task(2, 2, today.timeInMillis+1000*60*60*2, today.timeInMillis+1000*60*60*3, 0, 0, CompletionStatus.NOT_STARTED),
            Task(3, 1, today.timeInMillis+1000*60*60*24, today.timeInMillis+1000*60*60*25, 0, 0, CompletionStatus.NOT_STARTED),
            Task(4, 2, today.timeInMillis+1000*60*60*25, today.timeInMillis+1000*60*60*26, 0, 0, CompletionStatus.NOT_STARTED),
            Task(5, 0, today.timeInMillis+1000*60*60*26, today.timeInMillis+1000*60*60*27, 0, 0, CompletionStatus.NOT_STARTED),
            Task(6, 2, today.timeInMillis+1000*60*60*27, today.timeInMillis+1000*60*60*28, 0, 0, CompletionStatus.NOT_STARTED)
        )

        prepareViewModels(tasks, scheduledTasks)
    }

    private fun prepareViewModels(tasks: List<Task>, scheduledTasks: List<ScheduledTask>) {
        val modelList = mutableListOf<ItemViewModel>()

        val groupedByWeekdayTasks = groupTasksByWeekday(tasks)
        val timeFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)
        val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)
        val today = Calendar.getInstance()
        for (dt in groupedByWeekdayTasks.keys) {
            val cal = Calendar.getInstance()
            cal.time = dt
            val sectionTitle = if (today.get(Calendar.DAY_OF_WEEK) == cal.get(Calendar.DAY_OF_WEEK)) {
                resourceExtractor.getString(R.string.today)
            } else {
                dateFormat.format(dt)
            }
            modelList.add(SectionHeaderViewModel(sectionTitle))

            groupedByWeekdayTasks[dt]?.forEach {
                val schTask = scheduledTasks.find { st -> st.id == it.scheduledTaskId } ?: throw IllegalArgumentException()
                val title = schTask.name
                val from = timeFormat.format(Date(it.scheduled_time_start))
                val to = timeFormat.format(Date(it.scheduled_time_end))
                val period = resourceExtractor.getString(R.string.period_pattern, from, to)

                modelList.add(TaskViewModel(title, period))
            }
        }

        itemsLiveData.value = modelList
    }

    private fun groupTasksByWeekday(tasks: List<Task>): Map<Date, List<Task>> {
        val groupedTasks: MutableMap<Date, List<Task>> = mutableMapOf()

        val cal = Calendar.getInstance()
        val scheduledTaskCal = Calendar.getInstance()

        var weekday = cal[Calendar.DAY_OF_WEEK]
        while (true) {
            cal[Calendar.DAY_OF_WEEK] = weekday
            val tasksAtDay = tasks.filter {
                scheduledTaskCal.time = Date(it.scheduled_time_start)
                scheduledTaskCal[Calendar.DAY_OF_WEEK] == cal[Calendar.DAY_OF_WEEK]
            }
            if (tasksAtDay.isNotEmpty()) {
                groupedTasks[cal.time] = tasksAtDay
            }

            if (weekday == Calendar.SATURDAY) {
                weekday = Calendar.SUNDAY
                ++cal[Calendar.WEEK_OF_YEAR]
            } else if (weekday == Calendar.SUNDAY) {
                break
            } else {
                ++weekday
            }
        }

        return groupedTasks
    }

}
