package com.mashjulal.android.taplan.presentation.tasklist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashjulal.android.taplan.R
import kotlinx.android.synthetic.main.activity_task_list.*

class TaskListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        rv_tasks.adapter = TaskListRecyclerViewAdapter(listOf(
            CalendarDayModel("7", "Monday"),
            TaskModel("Task 1",
                "10:00",
                "10:30",
                "10:00",
                "10:30"),
            TaskModel("Task 2",
                "11:00",
                "11:30",
                "11:00",
                "11:30"),
            DividerModel,
            TaskModel("Task 3",
                "12:00",
                "12:30"),
            TaskModel("Task 4",
                "13:00",
                "13:30")
        ))
    }
}
