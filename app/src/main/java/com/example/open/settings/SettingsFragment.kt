package com.example.open.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.example.open.R
import com.example.open.presentation.view.DataDisplayFragment


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.sort_filter, rootKey)

        val preferences =
            PreferenceManager.getDefaultSharedPreferences(activity)
        val switchPreferenceName =
            findPreference(DataDisplayFragment.NAME_KEY) as SwitchPreferenceCompat?
        val switchPreferenceAge =
            findPreference(DataDisplayFragment.AGE_KEY) as SwitchPreferenceCompat?
        val switchPreferenceColor =
            findPreference(DataDisplayFragment.COLOR_KEY) as SwitchPreferenceCompat?

        if (preferences.getBoolean(DataDisplayFragment.NAME_KEY, false)) {
            switchPreferenceAge?.isEnabled = false
            switchPreferenceColor?.isEnabled = false
        }

        if (preferences.getBoolean(DataDisplayFragment.AGE_KEY, false)) {
            switchPreferenceName?.isEnabled = false
            switchPreferenceColor?.isEnabled = false
        }

        if (preferences.getBoolean(DataDisplayFragment.COLOR_KEY, false)) {
            switchPreferenceName?.isEnabled = false
            switchPreferenceAge?.isEnabled = false
        }

        switchPreferenceName?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val turned = newValue as Boolean
                if (turned) {
                    switchPreferenceAge?.isEnabled = false
                    switchPreferenceColor?.isEnabled = false
                } else {
                    switchPreferenceAge?.isEnabled = true
                    switchPreferenceColor?.isEnabled = true
                }
                preferences.edit().putBoolean(DataDisplayFragment.NAME_KEY, turned).apply()
                true
            }

        switchPreferenceAge?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val turned = newValue as Boolean
                if (turned) {
                    switchPreferenceName?.isEnabled = false
                    switchPreferenceColor?.isEnabled = false
                } else {
                    switchPreferenceName?.isEnabled = true
                    switchPreferenceColor?.isEnabled = true
                }
                preferences.edit().putBoolean(DataDisplayFragment.AGE_KEY, turned).apply()
                true
            }

        switchPreferenceColor?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val turned = newValue as Boolean
                if (turned) {
                    switchPreferenceName?.isEnabled = false
                    switchPreferenceAge?.isEnabled = false
                } else {
                    switchPreferenceName?.isEnabled = true
                    switchPreferenceAge?.isEnabled = true
                }
                preferences.edit().putBoolean(DataDisplayFragment.COLOR_KEY, turned).apply()
                true
            }
    }

}