package com.mashjulal.android.taplan.presentation.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.main.MainActivity
import com.mashjulal.android.taplan.presentation.scheduledtasks.ScheduledTasksActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DELAY_MILLIS = 1 * 1000L

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch(Dispatchers.IO) {
            delay(DELAY_MILLIS)
            launch(Dispatchers.Main) {
                routeToScreen()
            }
        }
    }

    private fun routeToScreen() {
        startActivity(Intent(this, ScheduledTasksActivity::class.java))
        finish()
    }
}
