package com.mashjulal.android.taplan.di

import androidx.room.Room
import com.mashjulal.android.taplan.data.db.AppDatabase
import com.mashjulal.android.taplan.data.db.DatabaseSchema.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module (override = true) {
    single {
        Room.databaseBuilder(androidContext(),
            AppDatabase::class.java, DATABASE_NAME).build()
    }
}