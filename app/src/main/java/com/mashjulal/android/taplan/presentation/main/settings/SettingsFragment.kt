package com.mashjulal.android.taplan.presentation.main.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.utils.ThemeHelper.applyTheme


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val themePreference = findPreference("themePref") as ListPreference?
        themePreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val themeOption = newValue as String?
                applyTheme(themeOption!!)
                true
            }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SettingsFragment()
    }
}
