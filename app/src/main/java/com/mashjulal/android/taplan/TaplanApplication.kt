package com.mashjulal.android.taplan

import android.app.Application
import com.mashjulal.android.taplan.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TaplanApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TaplanApplication)
            modules(applicationModule)
        }
    }
}