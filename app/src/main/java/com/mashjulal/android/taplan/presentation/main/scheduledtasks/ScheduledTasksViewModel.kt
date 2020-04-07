package com.mashjulal.android.taplan.presentation.main.scheduledtasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.data.android.ResourceExtractor
import com.mashjulal.android.taplan.domain.scheduledtask.interactor.ScheduledTaskInteractor
import com.mashjulal.android.taplan.domain.task.interactor.TaskInteractor
import com.mashjulal.android.taplan.models.domain.ScheduledTask
import com.mashjulal.android.taplan.models.domain.Task
import com.mashjulal.android.taplan.models.presentation.SectionHeaderViewModel
import com.mashjulal.android.taplan.models.presentation.TaskViewModel
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ScheduledTasksViewModel(
    private val resourceExtractor: ResourceExtractor,
    private val taskInteractor: TaskInteractor,
    private val scheduledTaskInteractor: ScheduledTaskInteractor
) : ViewModel() {

    val itemsLiveData: MutableLiveData<List<ItemViewModel>> = MutableLiveData()

    init {
        viewModelScope.launch {
            val itemModels = mutableListOf<ItemViewModel>()
            withContext(Dispatchers.IO) {
                val tasks = scheduledTaskInteractor.getCurrentWeekTasks()
                val scheduledTasks = taskInteractor.getAllTasks()

                itemModels.addAll(prepareViewModels(tasks, scheduledTasks))
            }
            itemsLiveData.value = itemModels
        }
    }

    private fun prepareViewModels(tasks: List<Task>, scheduledTasks: List<ScheduledTask>): List<ItemViewModel> {
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

        return modelList
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
