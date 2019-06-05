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
        /*
        for (String prefKey : prefList) {
            findPreference(prefKey).setSummary(mData.get(SpHelper.getInt(this.getActivity(), prefKey, 0)).getName());
        }
        */
        findPreference("preference_up").setSummary(mData.get(SpHelper.getInt(this.getActivity(), "preference_up", 5)).getName());
        findPreference("preference_down").setSummary(mData.get(SpHelper.getInt(this.getActivity(), "preference_down", 7)).getName());
        findPreference("preference_left").setSummary(mData.get(SpHelper.getInt(this.getActivity(), "preference_left", 11)).getName());
        findPreference("preference_right").setSummary(mData.get(SpHelper.getInt(this.getActivity(), "preference_right", 11)).getName());

    }

    private void initData() {
        mData = new LinkedList<>();
        mData.add(new AppClassName("浏览器", "com.android.browser", "com.android.browser.BrowserActivity"));
        mData.add(new AppClassName("时钟", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui.activity.alarm.AlarmClockActivity"));
        mData.add(new AppClassName("相册", "com.android.gallery3d", "com.android.gallery3d.app" +
                ".GalleryActivity"));
        mData.add(new AppClassName("音乐", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui.activity.music.MusicActivity"));
        mData.add(new AppClassName("设置", "com.android.settings", "com" +
                ".android.settings.Settings"));
        mData.add(new AppClassName("相机", "org.codeaurora.snapcam", "com.android.camera" +
                ".CameraLauncher"));
        mData.add(new AppClassName("客服中心", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
        mData.add(new AppClassName("日历", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui.activity.calendar.CalendarActivity"));
        mData.add(new AppClassName("FM电台", "com.android.fmradio", "com.android.fmradio.FmMainActivity"));
        mData.add(new AppClassName("SoS紧急呼叫", "com.android.settings", "com.android.settings.SoSSettings"));
        mData.add(new AppClassName("录音机", "com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder"));
        mData.add(new AppClassName("文件管理", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
        //mData.add(new AppClassName("亲情号码", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
        mData.add(new AppClassName("计算器", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui" +
                ".activity.caculator.SimpleCalculatorActivity"));
        mData.add(new AppClassName("短信", "com.ctyon.ctyonlauncher", "com.ctyon" +
                ".ctyonlauncher.ui.activity.message.MessageMainActivity"));
        mData.add(new AppClassName("翼支付", "com.chinatelecom.bestpayclient", "com" +
                ".chinatelecom.bestpayclient.ui.activity.GuideActivity"));
        mData.add(new AppClassName("一键清除", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
    }
}

