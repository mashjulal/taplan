package com.mashjulal.android.taplan.presentation.main.tasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashjulal.android.taplan.domain.task.interactor.TaskInteractor
import com.mashjulal.android.taplan.models.presentation.ScheduledTaskViewModel
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksViewModel(
    private val taskInteractor: TaskInteractor
) : ViewModel() {

    val itemsLiveData: MutableLiveData<List<ItemViewModel>> = MutableLiveData()

    init {
        refreshTasksList()
    }

    fun refreshTasksList() {
        viewModelScope.launch {
            val tasks: MutableList<ScheduledTaskViewModel> = mutableListOf()
            withContext(Dispatchers.IO) {
                tasks.addAll(taskInteractor.getAllTasks().map { ScheduledTaskViewModel(it) })
            }
            itemsLiveData.value = tasks
        }
    }

}
