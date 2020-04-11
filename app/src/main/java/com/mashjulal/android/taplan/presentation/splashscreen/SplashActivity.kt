package com.mashjulal.android.taplan.presentation.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.data.scheduler.NotificationSchedulerImpl
import com.mashjulal.android.taplan.data.scheduler.TaskSchedulerImpl
import com.mashjulal.android.taplan.presentation.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val taskScheduler by inject<TaskSchedulerImpl>()
    private val notificationScheduler by inject<NotificationSchedulerImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch(Dispatchers.IO) {
            taskScheduler.schedule()
            notificationScheduler.schedule()
            launch(Dispatchers.Main) {
                routeToScreen()
            }
        }
    }

    private fun routeToScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
