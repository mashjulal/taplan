package com.mashjulal.android.taplan.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.edittask.EditTaskActivity
import com.mashjulal.android.taplan.presentation.main.scheduledtasks.ScheduledTasksFragment
import com.mashjulal.android.taplan.presentation.main.settings.SettingsFragment
import com.mashjulal.android.taplan.presentation.main.today.TodayFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.fab_new_task
import kotlinx.android.synthetic.main.fragment_scheduled_tasks.*

class MainActivity : AppCompatActivity() {

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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun selectBottomItem(menuItemId: Int) {
        val fr = getFragmentForId(menuItemId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fr)
            .commit()
    }

    private fun getFragmentForId(itemId: Int): Fragment {
        return when (itemId) {
            R.id.mi_today -> TodayFragment.newInstance()
            R.id.mi_events -> ScheduledTasksFragment.newInstance()
            R.id.mi_tasks -> ScheduledTasksFragment.newInstance()
            R.id.mi_settings -> SettingsFragment.newInstance()
            else -> throw IllegalArgumentException("Unknown menu item id: $itemId")
        }
    }
}
