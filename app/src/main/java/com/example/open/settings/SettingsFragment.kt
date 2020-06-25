package com.example.open.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.open.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.sort_filter)
    }
}