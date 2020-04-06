package com.mashjulal.android.taplan.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.edittask.EditTaskActivity
import com.mashjulal.android.taplan.presentation.main.scheduledtasks.ScheduledTasksFragment
import com.mashjulal.android.taplan.presentation.main.tasks.TasksFragment
import com.mashjulal.android.taplan.presentation.main.settings.SettingsFragment
import com.mashjulal.android.taplan.presentation.main.today.TodayFragment
import com.mashjulal.android.taplan.presentation.task.AboutTaskActivity
import com.mashjulal.android.taplan.presentation.utils.activity.ToolbarCustomizable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.fab_new_task

class MainActivity : AppCompatActivity(), ToolbarCustomizable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            selectBottomItem(it.itemId)
            true
        }
        selectBottomItem(R.id.mi_today)

        fab_new_task.setOnClickListener { startActivityForResult(
            EditTaskActivity.newIntent(this),
            EditTaskActivity.REQUEST_CODE_NEW
        ) }

        setSupportActionBar(toolbar)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            EditTaskActivity.REQUEST_CODE_NEW, AboutTaskActivity.REQUEST_CODE_SHOW ->
                supportFragmentManager.findFragmentByTag(TasksFragment.TAG)
                    ?.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun setTitles(title: String, subtitle: String) {
        toolbar.title = title
        toolbar.subtitle = subtitle
    }

    private fun selectBottomItem(menuItemId: Int) {
        val (fr, tag) = getFragmentForId(menuItemId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fr, tag)
            .commit()
    }

    private fun getFragmentForId(itemId: Int): Pair<Fragment, String> {
        return when (itemId) {
            R.id.mi_today -> TodayFragment.newInstance() to TodayFragment.TAG
            R.id.mi_events -> ScheduledTasksFragment.newInstance() to ScheduledTasksFragment.TAG
            R.id.mi_tasks -> TasksFragment.newInstance() to TasksFragment.TAG
            R.id.mi_settings -> SettingsFragment.newInstance() to SettingsFragment.TAG
            else -> throw IllegalArgumentException("Unknown menu item id: $itemId")
        }
    }
}
