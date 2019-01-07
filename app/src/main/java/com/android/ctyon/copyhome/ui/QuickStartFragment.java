package com.android.ctyon.copyhome.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.adapter.data.AppClassName;
import com.android.ctyon.copyhome.utils.SpHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QuickStartFragment extends PreferenceFragment implements Preference
        .OnPreferenceClickListener {
    private static final String                   TAG = "QuickStartFragment";
    private              PreferenceCategory       mPreferenceCategory;
    private              List<String>             prefList;
    private              List<String>             pkgList;
    private              List<String>             clsNameList;
    private              LinkedList<AppClassName> mData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_quick_start);
        prefList = Arrays.asList(getResources().getStringArray(R.array.quick_start_pref_keys));
        pkgList = Arrays.asList(getResources().getStringArray(R.array.applist_package_name));
        clsNameList = Arrays.asList(getResources().getStringArray(R.array.applist_class_name));
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Log.d(TAG, "onPreferenceClick: ");

        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        Log.d(TAG, "onPreferenceTreeClick: " + preference.getKey());
        Intent intent = new Intent();
        intent.putExtra("preference_key", preference.getKey());
        intent.setComponent(new ComponentName("com.android.ctyon.copyhome", "com.android.ctyon" +
                ".copyhome.ui.AppListActivity"));
        startActivityForResult(intent, 2);
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(null == data){
            return;
        }
        switch (requestCode) {
            case 2:
                String prefKey = data.getStringExtra("pref_key");
                String appName = data.getStringExtra("name");
                int appIndex = data.getIntExtra("position", 0);
                findPreference(prefKey).setSummary(appName);
                SpHelper.putInt(this.getActivity(), prefKey, appIndex);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        initView();

    }



    private void initView() {
        initData();
        for (String prefKey : prefList) {
            findPreference(prefKey).setSummary(mData.get(SpHelper.getInt(this.getActivity(), prefKey, 0)).getName());
        }
    }

    private void initData() {
        mData = new LinkedList<>();
        mData.add(new AppClassName("拍照", "org.codeaurora.snapcam", "com.android.camera" +
                ".CameraLauncher"));
        mData.add(new AppClassName("相册", "com.android.gallery3d", "com.android.gallery3d.app" +
                ".GalleryActivity"));
        mData.add(new AppClassName("音乐", "com.android.music", "com.android.music" +
                ".MusicBrowserActivity"));
        mData.add(new AppClassName("收音机", "com.android.fmradio", "com.android.fmradio" +
                ".FmMainActivity"));
        mData.add(new AppClassName("录音机", "com.android.soundrecorder", "com.android.soundrecorder" +
                ".SoundRecorder"));
        mData.add(new AppClassName("文件管理", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
    }
}

