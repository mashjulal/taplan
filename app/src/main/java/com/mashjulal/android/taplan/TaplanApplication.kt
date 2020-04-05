package com.mashjulal.android.taplan

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.mashjulal.android.taplan.di.applicationModule
import com.mashjulal.android.taplan.di.editTaskListModule
import com.mashjulal.android.taplan.di.scheduledTaskListModule
import com.mashjulal.android.taplan.presentation.utils.ThemeHelper
import com.mashjulal.android.taplan.presentation.utils.ThemeHelper.applyTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class TaplanApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TaplanApplication)
            modules(listOf(applicationModule, scheduledTaskListModule, editTaskListModule))
        }

        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = sharedPreferences.getString("themePref", ThemeHelper.DEFAULT_MODE) ?: throw IllegalArgumentException()
        applyTheme(themePref)
    }
}