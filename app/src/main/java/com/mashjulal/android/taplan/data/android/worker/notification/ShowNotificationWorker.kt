package com.mashjulal.android.taplan.data.android.worker.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.data.android.ResourceExtractor
import com.mashjulal.android.taplan.data.android.worker.WorkerFactoryCreator
import com.mashjulal.android.taplan.presentation.splashscreen.SplashActivity

private const val CHANNEL_ID = "scheduledNotificationsChannel"
private const val NOTIFICATION_ID = 1

class ShowNotificationWorker(
    private val resourceExtractor: ResourceExtractor,
    context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {

    override fun doWork(): Result {
        val taskTitle = inputData.getString("task title")

        createNotificationChannel()

        val intent = Intent(applicationContext, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_time_24dp)
            .setContentTitle(taskTitle)
            .setContentText("Will start in 5 minutes")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, notification)
        }

        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Scheduled tasks channel"
            val desc = ""
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = desc
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        class FactoryCreator(
            private val resourceExtractor: ResourceExtractor
        ): WorkerFactoryCreator {
            override fun create(context: Context, workerParameters: WorkerParameters) =
                ShowNotificationWorker(
                    resourceExtractor,
                    context,
                    workerParameters
                )
        }
    }
}