package com.mashjulal.android.taplan.data.android.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class TaplanWorkerFactory(
    private val workerCreators: Map<Class<out ListenableWorker>, WorkerFactoryCreator>
): WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return workerCreators[Class.forName(workerClassName)]?.create(appContext, workerParameters)
    }
}