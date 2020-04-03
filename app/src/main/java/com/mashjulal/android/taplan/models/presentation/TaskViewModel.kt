package com.mashjulal.android.taplan.models.presentation

import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel

data class TaskViewModel(
    val title: String,
    val period: String
) : ItemViewModel