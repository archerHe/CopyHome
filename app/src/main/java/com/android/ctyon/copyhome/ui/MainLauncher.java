package com.android.ctyon.copyhome.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
            case KeyEvent.KEYCODE_ENTER:
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
                quickStart("preference_up", 5);
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_DPAD_DOWN:
                quickStart("preference_down", 7);
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_DPAD_LEFT:
                quickStart("preference_left", 16);
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                quickStart("preference_right", 11);
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
        //tv_contect.setTextColor(Color.rgb(0, 0, 0));
        //tv_menu.setTextColor(Color.rgb(0, 0, 0));
        tv_menu.setText(R.string.main_menu);
        updateSimCarrier();
    }

    private void updateLunar() {
        tvLunar.setText(new Lunar(Calendar.getInstance()).getChineseLunar());
    }

    private void initAppInfoList() {
        mAppInfoList = new LinkedList<>();
        mAppInfoList.add(new AppClassName("浏览器", "com.android.browser", "com.android.browser.BrowserActivity"));
        mAppInfoList.add(new AppClassName("时钟", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui.activity.alarm.AlarmClockActivity"));
        mAppInfoList.add(new AppClassName("相册", "com.android.gallery3d", "com.android.gallery3d.app" +
                ".GalleryActivity"));
        mAppInfoList.add(new AppClassName("音乐", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui.activity.music.MusicActivity"));
        mAppInfoList.add(new AppClassName("设置", "com.android.settings", "com" +
                ".android.settings.Settings"));
        mAppInfoList.add(new AppClassName("相机", "org.codeaurora.snapcam", "com.android.camera" +
                ".CameraLauncher"));
        mAppInfoList.add(new AppClassName("客服中心", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
        mAppInfoList.add(new AppClassName("日历", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui" +
                ".activity.calendar.CalendarActivity"));
        mAppInfoList.add(new AppClassName("FM电台", "com.android.fmradio", "com.android.fmradio.FmMainActivity"));
        mAppInfoList.add(new AppClassName("SoS紧急呼叫", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
        mAppInfoList.add(new AppClassName("录音机", "com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder"));
        mAppInfoList.add(new AppClassName("文件管理", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
        mAppInfoList.add(new AppClassName("亲情号码", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
        mAppInfoList.add(new AppClassName("计算器", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui" +
                ".activity.caculator.SimpleCalculatorActivity"));
        mAppInfoList.add(new AppClassName("短信", "com.ctyon.ctyonlauncher", "com.ctyon" +
                ".ctyonlauncher.ui.activity.message.MessageMainActivity"));
        mAppInfoList.add(new AppClassName("yzf", "com.chinatelecom.bestpayclient", "com" +
                ".chinatelecom.bestpayclient.ui.activity.GuideActivity"));
        mAppInfoList.add(new AppClassName("一键清除", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
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

    private void updateSimCarrier(){
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        int simStatus = telephonyManager.getSimState();
        switch(simStatus){
            case TelephonyManager.SIM_STATE_ABSENT:
                tvCarrierInfo.setVisibility(View.VISIBLE);
                break;
        }
    }

}
