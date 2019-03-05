package com.android.ctyon.copyhome.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ctyon.copyhome.MyApplication;
import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.adapter.data.AppClassName;
import com.android.ctyon.copyhome.utils.LunarSolarConverter;
import com.android.ctyon.copyhome.utils.Lunar;
import com.android.ctyon.copyhome.utils.SpHelper;

import java.util.Calendar;
import java.util.LinkedList;

public class MainLauncher extends Activity {

    private TextView                 tvLunar;
    private TextView                 tvCarrierInfo;
    private LinkedList<AppClassName> mAppInfoList;

    private String[]    permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_launcher);
        initAppInfoList();
        initView();
        updateLunar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                //showDialogTipUserRequestPermission();
                startRequestPermission();
            }
        }
    }

    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("由于Launcher需要获取存储空间，扫描铃声；\n否则，您将无法正常使用自定义铃声功能")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        //showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许支付宝使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
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
                return super.onKeyUp(keyCode, event);
            case KeyEvent.KEYCODE_STAR:
                startCallNumber(KeyEvent.KEYCODE_STAR);
                return super.onKeyUp(keyCode, event);
            case KeyEvent.KEYCODE_POUND:
                startCallNumber(KeyEvent.KEYCODE_POUND);
                return super.onKeyUp(keyCode, event);
            case KeyEvent.KEYCODE_CALL:
                Intent intent1 = new Intent();
                ComponentName componentName = new ComponentName("com.ctyon.ctyonlauncher", "com.ctyon" +
                        ".ctyonlauncher.ui.activity.dialer.CallMainActivity");
                intent1.setComponent(componentName);
                startActivity(intent1);
                break;
            default:
                return super.onKeyUp(keyCode, event);
        }

        return super.onKeyUp(keyCode, event);
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
        mAppInfoList.add(new AppClassName("日历", "com.calendar2345", "com.calendar2345.activity.CalendarMainActivity"));
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
        int simStatus = 0;
        try{
            simStatus = telephonyManager.getSimState();
        }catch (NullPointerException e){

            tvCarrierInfo.setVisibility(View.INVISIBLE);
            return;
        }

        switch(simStatus){
            case TelephonyManager.SIM_STATE_ABSENT:
                tvCarrierInfo.setVisibility(View.VISIBLE);
                break;
        }
    }

}
