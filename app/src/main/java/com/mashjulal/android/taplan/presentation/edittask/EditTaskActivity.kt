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
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
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
        viewModel.titleLiveData.observe(this, Observer { updateTextInputField(it, til_task_title) })
        viewModel.hourInWeekLiveData.observe(this, Observer { updateTextInputField(it, til_hours_per_week) })
        viewModel.selectedStartTimeLiveData.observe(this, Observer { updateTimeField(it, true) })
        viewModel.selectedEndTimeLiveData.observe(this, Observer { updateTimeField(it, false) })

        viewModel.saveTaskCompleted.observe(this, Observer { closeActivity() })
        viewModel.validationPassed.observe(this, Observer { changeSaveButtonVisibility(it) })

        viewModel.hoursPerWeekValidationErrorLiveData.observe(this, Observer { updateWeekPerDayFieldError(it) })
        viewModel.titleValidationErrorLiveData.observe(this, Observer { updateTitleFieldError(it) })
        viewModel.startTimeValidationErrorLiveData.observe(this, Observer { updateTimeError(it, tv_start_time_error) })
        viewModel.endTimeValidationErrorLiveData.observe(this, Observer { updateTimeError(it, tv_end_time_error) })

        viewModel.validateTitle()
        viewModel.validateHourCount()

        val calendar = Calendar.getInstance()
        val selectedHours = calendar[Calendar.HOUR_OF_DAY]
        val selectedMinutes = calendar[Calendar.MINUTE]
        viewModel.validateStartTime(selectedHours to selectedMinutes)
        viewModel.validateEndTime(selectedHours to selectedMinutes+1)
    }

    private fun updateTimeError(error: String?, tvError: TextView) {
        tvError.text = error
        tvError.visibility = if (error.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun changeSaveButtonVisibility(visible: Boolean) {
        if (visible) {
            fab_save_task.show()
        } else {
            fab_save_task.hide()
        }
    }

    private fun updateWeekPerDayFieldError(error: String?) {
        til_hours_per_week.error = error
    }

    private fun updateTitleFieldError(error: String?) {
        til_task_title.error = error
    }

    private fun closeActivity() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun initViews() {
        section_start_time.setOnClickListener {
            val (hours, minutes) = viewModel.selectedStartTimeLiveData.value ?: throw IllegalArgumentException()
            showTimePickerDialog(hours, minutes) { viewModel.validateStartTime(it) }
        }
        section_end_time.setOnClickListener {
            val (hours, minutes) = viewModel.selectedEndTimeLiveData.value ?: throw IllegalArgumentException()
            showTimePickerDialog(hours, minutes) { viewModel.validateEndTime(it) }
        }
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

    private fun updateTextInputField(title: String, field: TextInputLayout) {
        if (title != field.editText?.text.toString()) {
            field.editText?.setText(title)
        }
    }

    private fun showTimePickerDialog(hours: Int, minutes: Int, onTimeSetListener: (Pair<Int, Int>) -> Unit) {
        TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute -> onTimeSetListener.invoke(hourOfDay to minute) },
            hours,
            minutes,
            true)
            .show()
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
