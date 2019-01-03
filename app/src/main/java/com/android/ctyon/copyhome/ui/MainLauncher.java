package com.android.ctyon.copyhome.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.utils.LunarSolarConverter;
import com.android.ctyon.copyhome.utils.Lunar;

import java.util.Calendar;

public class MainLauncher extends AppCompatActivity {

    private TextView tvLunar;
    private TextView tvCarrierInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_launcher);
        initView();
        updateLunar();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();

        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                intent.setComponent(new ComponentName("com.android.ctyon.copyhome", "com.android" +
                        ".ctyon.copyhome.Main2Activity"));
                break;
            case KeyEvent.KEYCODE_BACK:
                intent.setComponent(new ComponentName("com.ctyon.ctyonlauncher", "com.ctyon" +
                        ".ctyonlauncher.ui.activity.contacts.ContactMainActivity"));
                break;
            default:
                return super.onKeyDown(keyCode, event);
        }
        startActivity(intent);
        return super.onKeyDown(keyCode, event);
    }


    private void initView() {
        tvLunar = findViewById(R.id.lunar_tv);
        tvCarrierInfo = findViewById(R.id.carrierInfo_tv);
        View v = (View) findViewById(R.id.main_bottom_layout);
        TextView tv_contect = v.findViewById(R.id.back_tv);
        TextView tv_menu = v.findViewById(R.id.ok_tv);
        tv_contect.setText(R.string.main_contact);
        //tv_contect.setTextColor(Color.rgb(255, 255, 255));
        //tv_menu.setTextColor(Color.rgb(255, 255, 255));
        tv_menu.setText(R.string.main_menu);
    }

    private void updateLunar() {
        tvLunar.setText(new Lunar(Calendar.getInstance()).getChineseLunar());


    }

}
