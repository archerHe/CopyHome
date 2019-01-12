package com.android.ctyon.copyhome.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import com.android.ctyon.copyhome.MyApplication;
import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.adapter.data.AppClassName;
import com.android.ctyon.copyhome.utils.LunarSolarConverter;
import com.android.ctyon.copyhome.utils.Lunar;
import com.android.ctyon.copyhome.utils.SpHelper;

import java.util.Calendar;
import java.util.LinkedList;

public class MainLauncher extends AppCompatActivity {

    private TextView                 tvLunar;
    private TextView                 tvCarrierInfo;
    private LinkedList<AppClassName> mAppInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_launcher);
        initAppInfoList();
        initView();
        updateLunar();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("MainLauncher", "onKeyDown: " + keyCode);
        Intent intent = new Intent();

        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                intent.setComponent(new ComponentName("com.android.ctyon.copyhome", "com.android" +
                        ".ctyon.copyhome.Main2Activity"));
                startActivity(intent);
                break;
            case KeyEvent.KEYCODE_BACK:
                intent.setComponent(new ComponentName("com.ctyon.ctyonlauncher", "com.ctyon" +
                        ".ctyonlauncher.ui.activity.contacts.ContactMainActivity"));
                startActivity(intent);
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                quickStart("preference_up", 1);
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_DPAD_DOWN:
                quickStart("preference_down", 2);
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_DPAD_LEFT:
                quickStart("preference_left", 3);
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                quickStart("preference_right", 4);
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
                startCallNumber(keyCode - KeyEvent.KEYCODE_0);
                return super.onKeyDown(keyCode, event);

            default:
                return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
    }


    private void initView() {
        tvLunar = findViewById(R.id.lunar_tv);
        tvCarrierInfo = findViewById(R.id.carrierInfo_tv);
        View v = findViewById(R.id.main_bottom_layout);
        TextView tv_contect = v.findViewById(R.id.back_tv);
        TextView tv_menu = v.findViewById(R.id.ok_tv);
        tv_contect.setText(R.string.main_contact);
        tv_contect.setTextColor(Color.rgb(255, 255, 255));
        tv_menu.setTextColor(Color.rgb(255, 255, 255));
        tv_menu.setText(R.string.main_menu);
    }

    private void updateLunar() {
        tvLunar.setText(new Lunar(Calendar.getInstance()).getChineseLunar());
    }

    private void initAppInfoList() {
        mAppInfoList = new LinkedList<>();
        mAppInfoList.add(new AppClassName("拍照", "org.codeaurora.snapcam", "com.android.camera" +
                ".CameraLauncher"));
        mAppInfoList.add(new AppClassName("相册", "com.android.gallery3d", "com.android.gallery3d" +
                ".app.GalleryActivity"));
        mAppInfoList.add(new AppClassName("音乐", "com.android.music", "com.android.music" +
                ".MusicBrowserActivity"));
        mAppInfoList.add(new AppClassName("收音机", "com.android.fmradio", "com.android.fmradio" +
                ".FmMainActivity"));
        mAppInfoList.add(new AppClassName("录音机", "com.android.soundrecorder", "com.android" +
                ".soundrecorder.SoundRecorder"));
        mAppInfoList.add(new AppClassName("文件管理", "com.yarin.android.FileManager", "com.ctyon" +
                ".FileManager.FirstAct"));
    }

    private void quickStart(String prefKey, int defVal) {
        int appIndex = SpHelper.getInt(MainLauncher.this, prefKey, defVal);
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(mAppInfoList.get(appIndex).getPackageName
                (), mAppInfoList.get(appIndex).getClassName());
        intent.setComponent(componentName);
        startActivity(intent);
    }

    private void startCallNumber(int num) {
        Intent intent = new Intent();
        intent.putExtra("number", String.valueOf(num));
        ComponentName componentName = new ComponentName("com.ctyon.ctyonlauncher", "com.ctyon" +
                ".ctyonlauncher.ui.activity.dialer.CallActivity");
        intent.setComponent(componentName);
        startActivity(intent);
    }

}
