package com.mashjulal.android.taplan.models.presentation

import com.mashjulal.android.taplan.models.domain.ScheduledTask
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel

data class ScheduledTaskViewModel(
    val model: ScheduledTask
) : ItemViewModel {
    val title = model.name
}