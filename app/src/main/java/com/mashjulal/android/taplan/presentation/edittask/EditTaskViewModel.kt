package com.mashjulal.android.taplan.presentation.edittask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashjulal.android.taplan.presentation.utils.SingleLiveEvent

class EditTaskViewModel: ViewModel() {

    val selectedStartTimeLiveData: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val selectedEndTimeLiveData: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val saveTaskCompleted: SingleLiveEvent<Void> = SingleLiveEvent()

    fun saveTask() {
        saveTaskCompleted.call()
    }
}