package com.mashjulal.android.taplan.presentation.main.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.utils.ThemeHelper.applyTheme
import com.mashjulal.android.taplan.presentation.utils.activity.ToolbarCustomizable
import java.lang.ClassCastException


class SettingsFragment : PreferenceFragmentCompat() {

    private var toolbarCustomizable: ToolbarCustomizable? = null

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is ToolbarCustomizable) {
            throw ClassCastException()
        }
        toolbarCustomizable = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarCustomizable?.setTitles("Settings")
    }

    override fun onDetach() {
        super.onDetach()
        toolbarCustomizable = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SettingsFragment()
    }
}
