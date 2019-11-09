package com.mashjulal.android.taplan.presentation.tasklist

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import androidx.transition.Slide
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.calendar.CalendarFragment
import kotlinx.android.synthetic.main.activity_task_list.*
import java.text.SimpleDateFormat
import java.util.*

class TaskListActivity : AppCompatActivity(), CalendarFragment.OnCalendarChangedListener {

    private var calendarOpened = false

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

        button_open_calendar.setOnClickListener {
            if (calendarOpened) {
                closeCalendar()
                calendarOpened = false
            } else {
                openCalendar()
                calendarOpened = true
            }
        }

        val date = Calendar.getInstance()
        showCurrentMonth(date.get(Calendar.MONTH), date.get(Calendar.YEAR))
    }

    override fun onMonthChanged(month: Int, year: Int) {
        showCurrentMonth(month, year)
    }

    private fun openCalendar() {
        val day = Calendar.getInstance()

        val fr = CalendarFragment.newInstance(
            day.get(Calendar.DAY_OF_MONTH),
            day.get(Calendar.MONTH),
            day.get(Calendar.YEAR)
        ).apply {
            enterTransition = Slide(Gravity.TOP)
            exitTransition = Slide(Gravity.BOTTOM)
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.container_calendar, fr, CalendarFragment.TAG)
            .commit()
    }

    private fun closeCalendar() {
        supportFragmentManager.findFragmentByTag(CalendarFragment.TAG)?.let {
            supportFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
    }

    private fun showCurrentMonth(month: Int, year: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
        }

        val locale = ConfigurationCompat.getLocales(resources.configuration).get(0)
        val tdf = SimpleDateFormat("MMMM, yyyy", locale)
        tv_month.text = tdf.format(calendar.time).capitalize()
    }
}
