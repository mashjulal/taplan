package com.mashjulal.android.taplan.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.main.events.EventsFragment
import com.mashjulal.android.taplan.presentation.main.settings.SettingsFragment
import com.mashjulal.android.taplan.presentation.main.today.TodayFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            selectBottomItem(it.itemId)
            true
        }
        selectBottomItem(R.id.mi_today)
    }

    private fun selectBottomItem(menuItemId: Int) {
        val fr = getFragmentForId(menuItemId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fr)
            .commit()
        supportActionBar?.title = getTitleForId(menuItemId)
    }

    private fun getTitleForId(itemId: Int): String {
        return getString(when (itemId) {
            R.id.mi_today -> R.string.today
            R.id.mi_events -> R.string.events
            R.id.mi_settings -> R.string.settings
            else -> throw IllegalArgumentException("Unknown menu item id: $itemId")
        })
    }

    private fun getFragmentForId(itemId: Int): Fragment {
        return when (itemId) {
            R.id.mi_today -> TodayFragment.newInstance()
            R.id.mi_events -> EventsFragment.newInstance()
            R.id.mi_settings -> SettingsFragment.newInstance()
            else -> throw IllegalArgumentException("Unknown menu item id: $itemId")
        }
    }
}
