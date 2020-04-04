package com.mashjulal.android.taplan.presentation.edittask

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mashjulal.android.taplan.R
import kotlinx.android.synthetic.main.activity_edit_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class EditTaskActivity : AppCompatActivity() {

    private val viewModel by viewModel<EditTaskViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        initViews()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.selectedStartTimeLiveData.observe(this, Observer { updateTimeField(it, true) })
        viewModel.selectedEndTimeLiveData.observe(this, Observer { updateTimeField(it, false) })
        viewModel.saveTaskCompleted.observe(this, Observer { closeActivity() })

        val calendar = Calendar.getInstance()
        val selectedTimePair = calendar[Calendar.HOUR_OF_DAY] to calendar[Calendar.MINUTE]
        viewModel.selectedStartTimeLiveData.value = selectedTimePair
        viewModel.selectedEndTimeLiveData.value = selectedTimePair
    }

    private fun closeActivity() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun initViews() {
        section_start_time.setOnClickListener { showTimePickerDialog(viewModel.selectedStartTimeLiveData) }
        section_end_time.setOnClickListener { showTimePickerDialog(viewModel.selectedEndTimeLiveData) }
        fab_save_task.setOnClickListener { viewModel.saveTask() }
    }

    private fun updateTimeField(timePair: Pair<Int, Int>, isStartTimePair: Boolean) {
        val selectedTime = getString(R.string.time_hours_and_minutes_pattern, timePair.first, timePair.second)
        if (isStartTimePair) {
            tv_start_time.text = selectedTime
        } else {
            tv_end_time.text = selectedTime
        }
    }

    private fun showTimePickerDialog(timeLiveData: MutableLiveData<Pair<Int, Int>>) {
        timeLiveData.value?.let {
            TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->  timeLiveData.value = hourOfDay to minute},
                it.first,
                it.second,
                true)
                .show()
        }
    }

    companion object {
        const val REQUEST_CODE_NEW = 10

        fun newIntent(context: Context) = Intent(context, EditTaskActivity::class.java)
    }
}
