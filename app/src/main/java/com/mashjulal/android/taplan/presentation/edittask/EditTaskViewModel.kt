package com.mashjulal.android.taplan.presentation.edittask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashjulal.android.taplan.presentation.utils.SingleLiveEvent

private const val MAX_HOURS_PER_WEEK = 24 * 7

class EditTaskViewModel: ViewModel() {

    val selectedStartTimeLiveData: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val selectedEndTimeLiveData: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val saveTaskCompleted: SingleLiveEvent<Void> = SingleLiveEvent()
    val hoursPerWeekValidationErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val titleValidationErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val validationPassed: MutableLiveData<Boolean> = MutableLiveData()

    private val validationFieldsLiveDataList: List<MutableLiveData<String?>> = listOf(
        titleValidationErrorLiveData,
        hoursPerWeekValidationErrorLiveData
    )

    init {
        validateTitle(null)
        validateHourCount(null)
    }

    fun saveTask() {
        saveTaskCompleted.call()
    }

    fun validateTitle(title: String?) {
        val error = when {
            title.isNullOrEmpty() -> "Title can't be empty"
            else -> null
        }

        titleValidationErrorLiveData.value = error
        validateOtherFields()
    }

    fun validateHourCount(hours: String?) {
        val error = when {
            hours == null -> "Hour count must be filled"
            hours.toIntOrNull() == null -> "Field must contain only numeric characters"
            hours.toInt() == 0 -> "Hour count can't be equal to 0"
            hours.toInt() > MAX_HOURS_PER_WEEK -> "Hour count can't be bigger that total hours in week"
            else -> null
        }

        hoursPerWeekValidationErrorLiveData.value = error
        validateOtherFields()
    }

    private fun validateOtherFields() {
        validationPassed.value = validationFieldsLiveDataList.all { it.value.isNullOrEmpty() }
    }
}