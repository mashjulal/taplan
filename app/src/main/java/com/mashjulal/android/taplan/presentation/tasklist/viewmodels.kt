package com.mashjulal.android.taplan.presentation.tasklist

import com.mashjulal.android.taplan.BR
import com.mashjulal.android.taplan.R

sealed class TaskListViewModel {
    abstract val type: Int
    abstract val bindingType: Int
}

class TaskViewModel(
    val task: TaskModel
): TaskListViewModel() {
    override val type: Int
        get() = ITEM_TYPE

    override val bindingType: Int
        get() = BR.taskViewModel

    fun hasActualTime(): Boolean = task.actualStartTime != null && task.actualEndTime != null

    companion object {
        const val ITEM_TYPE = R.layout.item_task
    }
}

class CalendarDayViewModel(
    val calendarDay: CalendarDayModel
): TaskListViewModel() {
    override val type: Int
        get() = ITEM_TYPE

    override val bindingType: Int
        get() = BR.calendarDayViewModel

    companion object {
        const val ITEM_TYPE = R.layout.item_calendar_day
    }
}

object DividerViewModel: TaskListViewModel() {
    override val type: Int
        get() = ITEM_TYPE

    override val bindingType: Int
        get() = -1

    const val ITEM_TYPE = R.layout.item_current_time_position
}

sealed class TaskListModel

data class TaskModel(
    val name: String,
    val scheduledStartTime: String,
    val scheduledEndTime: String,
    val actualStartTime: String? = null,
    val actualEndTime: String? = null
): TaskListModel()

data class CalendarDayModel(
    val day: String,
    val weekday: String
): TaskListModel()

object DividerModel: TaskListModel()