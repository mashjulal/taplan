package com.mashjulal.android.taplan.presentation.dailyschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.view.timelineschedule.Event
import com.mashjulal.android.taplan.presentation.view.timelineschedule.EventView
import kotlinx.android.synthetic.main.activity_daily_schedule.*

class DailyScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_schedule)

        for (i in 0..23) {
            val myEventView = EventView(this,
                Event().apply {
                    title = "TTT".repeat(10)
                    startTime = (i).toFloat()
                    endTime = (i + 1.5f)
                }
            )

            timeLine.addEvent(myEventView)
        }
    }
}
