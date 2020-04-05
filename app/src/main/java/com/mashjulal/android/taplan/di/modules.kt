package com.mashjulal.android.taplan.di

import androidx.room.Room
import com.mashjulal.android.taplan.android.ResourceExtractor
import com.mashjulal.android.taplan.data.db.AppDatabase
import com.mashjulal.android.taplan.data.db.DatabaseSchema.DATABASE_NAME
import com.mashjulal.android.taplan.presentation.edittask.EditTaskViewModel
import com.mashjulal.android.taplan.presentation.main.scheduledtasks.ScheduledTasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module (override = true) {
    single {
        Room.databaseBuilder(androidContext(),
            AppDatabase::class.java, DATABASE_NAME).build()
    }
    single {
        ResourceExtractor(androidContext())
    }
}

val scheduledTaskListModule = module (override = true) {
    viewModel {
        ScheduledTasksViewModel(get())
    }
}

val editTaskListModule = module (override = true) {
    viewModel {
        EditTaskViewModel()
    }
}