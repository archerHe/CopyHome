package com.android.ctyon.copyhome.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.ctyon.copyhome.R;

public class SpeechSettingsFragment extends PreferenceFragment {
    private static final String TAG = "SpeechSettingsFragment";
    private SwitchPreference     voice_call;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefeference_speech_setting);
        voice_call = (SwitchPreference) findPreference("voice_call");
    }



    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        Log.d(TAG, "onPreferenceTreeClick: ");
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Settings.System.putInt(getActivity().getContentResolver(), "speech_call_number_on", voice_call.isChecked()? 1 : 0);

    }
}
