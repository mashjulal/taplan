package com.mashjulal.android.taplan.data.android.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

interface WorkerFactoryCreator {
    fun create(context: Context, workerParameters: WorkerParameters): ListenableWorker?
}