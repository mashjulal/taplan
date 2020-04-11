package com.mashjulal.android.taplan

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.mashjulal.android.taplan.data.android.worker.TaplanWorkerFactory
import com.mashjulal.android.taplan.di.*
import com.mashjulal.android.taplan.presentation.utils.ThemeHelper
import com.mashjulal.android.taplan.presentation.utils.ThemeHelper.applyTheme
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class TaplanApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TaplanApplication)
            modules(listOf(applicationModule, scheduledTaskListModule, editTaskListModule, taskListModule, aboutTaskModule, repositoryModule))
        }

        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = sharedPreferences.getString("themePref", ThemeHelper.DEFAULT_MODE) ?: throw IllegalArgumentException()
        applyTheme(themePref)

        WorkManager.initialize(this,
            Configuration.Builder()
                .setWorkerFactory(get<TaplanWorkerFactory>())
                .build())
    }
}