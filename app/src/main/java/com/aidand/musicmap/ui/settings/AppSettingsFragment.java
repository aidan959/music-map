package com.aidand.musicmap.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.aidand.musicmap.R;

public class AppSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey);
    }

}
