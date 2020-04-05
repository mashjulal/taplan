package com.mashjulal.android.taplan.presentation.edittask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashjulal.android.taplan.domain.task.interactor.TaskInteractor
import com.mashjulal.android.taplan.models.domain.ScheduledTask
import com.mashjulal.android.taplan.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MAX_HOURS_PER_WEEK = 24 * 7
private const val DEFAULT_TITLE = ""
private const val DEFAULT_HOURS_PER_WEEK = 20

class EditTaskViewModel(
    private val taskInteractor: TaskInteractor
): ViewModel() {

    val titleLiveData: MutableLiveData<String> = MutableLiveData()
    val hourInWeekLiveData: MutableLiveData<String> = MutableLiveData()
    val selectedStartTimeLiveData: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val selectedEndTimeLiveData: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    val saveTaskCompleted: SingleLiveEvent<Void> = SingleLiveEvent()
    val hoursPerWeekValidationErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val titleValidationErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val startTimeValidationErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val endTimeValidationErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val validationPassed: MutableLiveData<Boolean> = MutableLiveData()

    private val validationFieldsLiveDataList: List<MutableLiveData<String?>> = listOf(
        titleValidationErrorLiveData,
        hoursPerWeekValidationErrorLiveData,
        startTimeValidationErrorLiveData,
        endTimeValidationErrorLiveData
    )

    fun saveTask() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val name = titleLiveData.value ?: throw IllegalStateException()
                val hoursPerWeek = hourInWeekLiveData.value?.toInt() ?: throw IllegalStateException()
                val (fromHours, fromMinutes) = selectedStartTimeLiveData.value ?: throw IllegalStateException()
                val (toHours, toMinutes) = selectedEndTimeLiveData.value ?: throw IllegalStateException()
                taskInteractor.insertTask(ScheduledTask(
                    name = name,
                    hours_per_week = hoursPerWeek,
                    time_from = (fromHours + fromMinutes * 60).toLong(),
                    time_to = (toHours + toMinutes * 60).toLong()))
            }
            saveTaskCompleted.call()
        }
    }

    fun validateTitle(title: String? = DEFAULT_TITLE) {
        val error = when {
            title.isNullOrEmpty() -> "Title can't be empty"
            else -> null
        }

        titleLiveData.value = title
        titleValidationErrorLiveData.value = error
        validateOtherFields()
    }

    fun validateHourCount(hours: String? = DEFAULT_HOURS_PER_WEEK.toString()) {
        val error = when {
            hours == null -> "Hour count must be filled"
            hours.toIntOrNull() == null -> "Field must contain only numeric characters"
            hours.toInt() == 0 -> "Hour count can't be equal to 0"
            hours.toInt() > MAX_HOURS_PER_WEEK -> "Hour count can't be bigger that total hours in week"
            else -> null
        }

        hourInWeekLiveData.value = hours
        hoursPerWeekValidationErrorLiveData.value = error
        validateOtherFields()
    }

    fun validateStartTime(startTimePair: Pair<Int, Int>) {
        var error: String? = null

        val endTimePair = selectedEndTimeLiveData.value
        if (endTimePair != null && !validateTimePairs(startTimePair, endTimePair)) {
            error = "From time can't be after/at end time"
        }

        selectedStartTimeLiveData.value = startTimePair
        startTimeValidationErrorLiveData.value = error
        endTimeValidationErrorLiveData.value = null

        validateOtherFields()
    }

    private fun validateTimePairs(startTimePair: Pair<Int, Int>, endTimePair: Pair<Int, Int>): Boolean {
        val (startHour, startMinutes) = startTimePair
        val (endHour, endMinutes) = endTimePair

        val startTimeAfterEndTime = startHour > endHour || (startHour == endHour && startMinutes >= endMinutes)
        return !startTimeAfterEndTime
    }

    fun validateEndTime(endTimePair: Pair<Int, Int>) {
        var error: String? = null

        val startTimePair = selectedStartTimeLiveData.value
        if (startTimePair != null && !validateTimePairs(startTimePair, endTimePair)) {
            error = "End time can't be before/at start time"
        }

        selectedEndTimeLiveData.value = endTimePair
        endTimeValidationErrorLiveData.value = error
        startTimeValidationErrorLiveData.value = null

        validateOtherFields()
    }

    private fun validateOtherFields() {
        validationPassed.value = validationFieldsLiveDataList.all { it.value.isNullOrEmpty() }
    }
}