package com.mashjulal.android.taplan.di

import com.mashjulal.android.taplan.data.android.ResourceExtractor
import com.mashjulal.android.taplan.data.android.worker.scheduler.TaskSchedulerWorker
import com.mashjulal.android.taplan.data.android.worker.TaplanWorkerFactory
import com.mashjulal.android.taplan.data.android.worker.notification.ScheduledTaskNotificationWorker
import com.mashjulal.android.taplan.data.android.worker.notification.ShowNotificationWorker
import com.mashjulal.android.taplan.data.db.AppDatabase
import com.mashjulal.android.taplan.data.repository.ScheduledTaskRepositoryImpl
import com.mashjulal.android.taplan.data.repository.TaskRepositoryImpl
import com.mashjulal.android.taplan.data.scheduler.NotificationSchedulerImpl
import com.mashjulal.android.taplan.data.scheduler.TaskSchedulerImpl
import com.mashjulal.android.taplan.domain.scheduledtask.ScheduledTaskRepository
import com.mashjulal.android.taplan.domain.scheduledtask.interactor.ScheduledTaskInteractor
import com.mashjulal.android.taplan.domain.scheduledtask.interactor.ScheduledTaskInteractorImpl
import com.mashjulal.android.taplan.domain.scheduledtask.register.ScheduledTaskRegister
import com.mashjulal.android.taplan.domain.scheduledtask.register.ScheduledTaskRegisterImpl
import com.mashjulal.android.taplan.domain.task.TaskRepository
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

    single {
        val creators = mapOf(
            TaskSchedulerWorker::class.java to get<TaskSchedulerWorker.Companion.FactoryCreator>(),
            ScheduledTaskNotificationWorker::class.java to get<ScheduledTaskNotificationWorker.Companion.FactoryCreator>(),
            ShowNotificationWorker::class.java to get<ShowNotificationWorker.Companion.FactoryCreator>()
        )
        TaplanWorkerFactory(creators)
    }

    single {
        TaskSchedulerImpl(androidContext())
    }

    single {
        NotificationSchedulerImpl(androidContext())
    }

    single {
        TaskSchedulerWorker.Companion.FactoryCreator(get(), get())
    }

    single {
        ScheduledTaskNotificationWorker.Companion.FactoryCreator(get(), get())
    }

    single {
        ShowNotificationWorker.Companion.FactoryCreator(get())
    }

    factory {
        get<AppDatabase>().getScheduledTaskDao()
    }

    factory {
        get<AppDatabase>().getTaskDao()
    }
}

val repositoryModule = module (override = true) {
    factory<TaskRepository> {
        TaskRepositoryImpl(get())
    }

    factory<ScheduledTaskRepository> {
        ScheduledTaskRepositoryImpl(get())
    }
}

val scheduledTaskListModule = module (override = true) {

    factory<ScheduledTaskRegister> {
        ScheduledTaskRegisterImpl(get(), get())
    }

    factory<ScheduledTaskInteractor> {
        ScheduledTaskInteractorImpl(get())
    }

    viewModel {
        ScheduledTasksViewModel(get(), get(), get())
    }
}

val taskListModule = module (override = true) {

    factory<TaskInteractor> {
        TaskInteractorImpl(get(), get())
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