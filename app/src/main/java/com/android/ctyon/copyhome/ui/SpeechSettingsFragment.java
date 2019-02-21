package com.android.ctyon.copyhome.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.utils.SpHelper;

public class SpeechSettingsFragment extends PreferenceFragment  implements Preference.OnPreferenceChangeListener{
    private static final String TAG = "SpeechSettingsFragment";
    private SwitchPreference     voice_call;
    private SwitchPreference    mSwitchPreferenceSpeechTime;
    private EditTextPreference mEditTextPreferenceBeginTime;
    private EditTextPreference mEditTextPreferenceEndTime;
    private PreferenceScreen  mPreferenceScreen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefeference_speech_setting);
        voice_call = (SwitchPreference) findPreference("voice_call");
        mEditTextPreferenceBeginTime = (EditTextPreference) findPreference("et_time_settings_begin");
        mEditTextPreferenceBeginTime.setOnPreferenceChangeListener(this);
        mEditTextPreferenceEndTime = (EditTextPreference) findPreference("et_time_settings_end");
        mEditTextPreferenceEndTime.setOnPreferenceChangeListener(this);
        mEditTextPreferenceBeginTime.setSummary(SpHelper.getString(getActivity(), "et_time_settings_begin" + "点", "8"));
        mEditTextPreferenceEndTime.setSummary(SpHelper.getString(getActivity(), "et_time_settings_end" + "点", "18"));
        mSwitchPreferenceSpeechTime = (SwitchPreference)findPreference("speech_time");
        mPreferenceScreen = (PreferenceScreen)findPreference("preferenceScreen");
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        preference.setSummary((String)o + "点");
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        if(!mSwitchPreferenceSpeechTime.isChecked()){
            mPreferenceScreen.removePreference(mEditTextPreferenceBeginTime);
            mPreferenceScreen.removePreference(mEditTextPreferenceEndTime);
        }else{
            mPreferenceScreen.addPreference(mEditTextPreferenceBeginTime);
            mPreferenceScreen.addPreference(mEditTextPreferenceEndTime);
        }
        mEditTextPreferenceBeginTime.setSummary(SpHelper.getString(getActivity(), "et_time_settings_begin", "8"));
        mEditTextPreferenceEndTime.setSummary(SpHelper.getString(getActivity(), "et_time_settings_end", "18"));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        Log.d(TAG, "onPreferenceTreeClick: ");
        if(preference.getKey().equals("speech_time")){
            if(!mSwitchPreferenceSpeechTime.isChecked()){
               mPreferenceScreen.removePreference(mEditTextPreferenceBeginTime);
               mPreferenceScreen.removePreference(mEditTextPreferenceEndTime);
            }else{
                mPreferenceScreen.addPreference(mEditTextPreferenceBeginTime);
                mPreferenceScreen.addPreference(mEditTextPreferenceEndTime);
            }
        }


        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Intent intent = new Intent("com.copyhome.alarm");
        if(mSwitchPreferenceSpeechTime.isChecked()){
            Log.d(TAG, "isChecked: ");
            getActivity().sendBroadcast(intent);
        }

        //Settings.System.putInt(getActivity().getContentResolver(), "speech_call_number_on", voice_call.isChecked()? 1 : 0);

    }
}
