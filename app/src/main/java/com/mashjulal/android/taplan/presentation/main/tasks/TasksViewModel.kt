package com.mashjulal.android.taplan.presentation.main.scheduledtasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashjulal.android.taplan.models.domain.ScheduledTask
import com.mashjulal.android.taplan.models.presentation.TaskViewModel
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel

class TasksViewModel : ViewModel() {

    val itemsLiveData: MutableLiveData<List<ItemViewModel>> = MutableLiveData()

    init {
        val scheduledTasks = listOf(
            ScheduledTask(0, "Task 1", 40, 0, 0),
            ScheduledTask(1, "Task 2", 40, 0, 0),
            ScheduledTask(2, "Task 3", 40, 0, 0)
        )

        itemsLiveData.value = scheduledTasks.map { TaskViewModel(it.name, "") }
    }

    fun refreshTasksList() {
        val scheduledTasks = listOf(
            ScheduledTask(0, "Task 1", 40, 0, 0),
            ScheduledTask(1, "Task 2", 40, 0, 0),
            ScheduledTask(2, "Task 3", 40, 0, 0),
            ScheduledTask(3, "Task 4", 40, 0, 0)
        )

        itemsLiveData.value = scheduledTasks.map { TaskViewModel(it.name, "") }
    }

}
