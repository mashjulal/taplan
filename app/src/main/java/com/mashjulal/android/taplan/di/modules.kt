package com.mashjulal.android.taplan.di

import com.mashjulal.android.taplan.data.android.ResourceExtractor
import com.mashjulal.android.taplan.data.db.AppDatabase
import com.mashjulal.android.taplan.data.repository.TaskRepositoryImpl
import com.mashjulal.android.taplan.domain.task.interactor.TaskInteractor
import com.mashjulal.android.taplan.domain.task.interactor.TaskInteractorImpl
import com.mashjulal.android.taplan.presentation.edittask.EditTaskViewModel
import com.mashjulal.android.taplan.presentation.main.scheduledtasks.ScheduledTasksViewModel
import com.mashjulal.android.taplan.presentation.main.tasks.TasksViewModel
import com.mashjulal.android.taplan.presentation.task.AboutTaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module (override = true) {
    single {
        AppDatabase.build(androidContext())
    }

    single {
        ResourceExtractor(androidContext())
    }

    factory {
        get<AppDatabase>().getScheduledTaskDao()
    }

    factory {
        get<AppDatabase>().getTaskDao()
    }
}

val scheduledTaskListModule = module (override = true) {
    viewModel {
        ScheduledTasksViewModel(get())
    }
}

val taskListModule = module (override = true) {

    factory<TaskInteractor> {
        TaskInteractorImpl(TaskRepositoryImpl(get()))
    }

    viewModel {
        TasksViewModel(get())
    }
}

val editTaskListModule = module (override = true) {
    viewModel {
        EditTaskViewModel(get())
    }
}

val aboutTaskModule = module (override = true) {
    viewModel {
        AboutTaskViewModel(get(), get())
    }
}