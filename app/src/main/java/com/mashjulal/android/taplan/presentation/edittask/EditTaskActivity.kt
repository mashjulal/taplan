package com.mashjulal.android.taplan.presentation.edittask

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel() {
        viewModel.selectedStartTimeLiveData.observe(this, Observer { updateTimeField(it, true) })
        viewModel.selectedEndTimeLiveData.observe(this, Observer { updateTimeField(it, false) })
        viewModel.saveTaskCompleted.observe(this, Observer { closeActivity() })
        viewModel.hoursPerWeekValidationErrorLiveData.observe(this, Observer { updateWeekPerDayField(it) })
        viewModel.titleValidationErrorLiveData.observe(this, Observer { updateTitleField(it) })
        viewModel.validationPassed.observe(this, Observer { changeSaveButtonVisibility(it) })

        val calendar = Calendar.getInstance()
        val selectedTimePair = calendar[Calendar.HOUR_OF_DAY] to calendar[Calendar.MINUTE]
        viewModel.selectedStartTimeLiveData.value = selectedTimePair
        viewModel.selectedEndTimeLiveData.value = selectedTimePair
        viewModel.validationPassed.value = false
    }

    private fun changeSaveButtonVisibility(visible: Boolean) {
        if (visible) {
            fab_save_task.show()
        } else {
            fab_save_task.hide()
        }
    }

    private fun updateWeekPerDayField(error: String?) {
        til_hours_per_week.error = error
    }

    private fun updateTitleField(error: String?) {
        til_task_title.error = error
    }

    private fun closeActivity() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun initViews() {
        section_start_time.setOnClickListener { showTimePickerDialog(viewModel.selectedStartTimeLiveData) }
        section_end_time.setOnClickListener { showTimePickerDialog(viewModel.selectedEndTimeLiveData) }
        fab_save_task.setOnClickListener { viewModel.saveTask() }
        til_hours_per_week.editText?.addTextChangedListener(ValidationTextWatcher { viewModel.validateHourCount(it) })
        til_task_title.editText?.addTextChangedListener(ValidationTextWatcher { viewModel.validateTitle(it) })

        toolbar.title = ""
        setSupportActionBar(toolbar)
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

private class ValidationTextWatcher(
    private val validateValueFunction: (String) -> Unit
): TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        validateValueFunction.invoke(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

}
