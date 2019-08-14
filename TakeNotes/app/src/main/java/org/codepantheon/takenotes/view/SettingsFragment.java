package org.codepantheon.takenotes.view;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import org.codepantheon.takenotes.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
