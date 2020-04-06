package com.mashjulal.android.taplan.presentation.task

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.databinding.ActivityAboutTaskBinding
import com.mashjulal.android.taplan.models.domain.ScheduledTask
import com.mashjulal.android.taplan.presentation.edittask.EditTaskActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class AboutTaskActivity : AppCompatActivity() {

    private val viewModel by viewModel<AboutTaskViewModel>()
    private lateinit var binding: ActivityAboutTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_task)
        initViewModel()
        binding.aboutTaskViewModel = viewModel

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about_task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> { finish(); true }
        R.id.mi_edit_task -> { viewModel.editTask(); true }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (EditTaskActivity.REQUEST_CODE_EDIT == requestCode && Activity.RESULT_OK == resultCode) {
            viewModel.updateTask()
            setResult(Activity.RESULT_OK)
        }
    }

    private fun initViewModel() {
        viewModel.taskLiveData.value = intent.getParcelableExtra(PARAM_TASK)
        viewModel.taskLiveData.observe(this, Observer { binding.invalidateAll() })
        viewModel.editTaskSingleEvent.observe(this, Observer { openEditScreen(it) })
    }

    private fun openEditScreen(task: ScheduledTask?) {
        startActivityForResult(EditTaskActivity.newIntent(this, task), EditTaskActivity.REQUEST_CODE_EDIT)
    }

    companion object {
        const val REQUEST_CODE_SHOW = 3

        private const val PARAM_TASK = "PARAM_TASK"

        fun newIntent(context: Context, scheduledTask: ScheduledTask) =
            Intent(context, AboutTaskActivity::class.java).apply {
                putExtra(PARAM_TASK, scheduledTask)
        }
    }
}
