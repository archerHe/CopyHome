package com.android.ctyon.copyhome;

import com.android.ctyon.copyhome.adapter.data.AppClassName;
import com.android.ctyon.copyhome.ui.MainViewPager;


import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.ctyon.copyhome.adapter.MainPagerAdapter;
import com.android.ctyon.copyhome.ui.MainViewPager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

public class Main2Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "Main2Activity";

    private MainViewPager            mMainViewPager;
    private MainPagerAdapter         mMainPagerAdapter;
    private ArrayList<View>          mViewArrayList;
    private LinkedList<String>       titleList;
    private Handler                  mHandler;
    private TextView                 tv_title;
    private LinkedList<AppClassName> mActivityClassNameList;
    private int                      mCurrentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mMainViewPager = findViewById(R.id.viewpager);
        mActivityClassNameList = new LinkedList<>();
        mViewArrayList = new ArrayList<>();
        initActivityClassNameList();
        initTitleList();
        LayoutInflater layoutInflater = getLayoutInflater();
        mViewArrayList.add(layoutInflater.inflate(R.layout.others_layout, null, false));
        mViewArrayList.add(layoutInflater.inflate(R.layout.contact_layout, null, false));
        mViewArrayList.add(layoutInflater.inflate(R.layout.call_layout, null, false));
        mViewArrayList.add(layoutInflater.inflate(R.layout.sms_layout, null, false));
        mViewArrayList.add(layoutInflater.inflate(R.layout.media_layout, null, false));
        mViewArrayList.add(layoutInflater.inflate(R.layout.tools_layout, null, false));
        mViewArrayList.add(layoutInflater.inflate(R.layout.setting_layout, null, false));
        mViewArrayList.add(layoutInflater.inflate(R.layout.wx_layout, null, false));
        mViewArrayList.add(layoutInflater.inflate(R.layout.yzf_layout, null, false));
        mViewArrayList.add(layoutInflater.inflate(R.layout.others_layout, null, false));
        mViewArrayList.add(layoutInflater.inflate(R.layout.contact_layout, null, false));
        mMainPagerAdapter = new MainPagerAdapter(mViewArrayList);
        mMainViewPager.setAdapter(mMainPagerAdapter);
        mMainViewPager.addOnPageChangeListener(this);
        mMainViewPager.setCurrentItem(1);
        //mMainViewPager.setCurrentItem(mViewArrayList.size() - 2, false);
        //mMainViewPager.setVisibility(View.INVISIBLE);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){

                }
                super.handleMessage(msg);
            }
        };
        //mHandler.postDelayed(mRunnable, 3000);
    /*
        mMainViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.android.ctyon.copyhome", "com.android.ctyon.copyhome.ui.OtherAppsActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        }, 100);
    */

    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //mMainViewPager.setVisibility(View.VISIBLE);
            mMainViewPager.setCurrentItem(1, false);
            Log.d(TAG, "run..");
        }
    };

    @Override
    public void onPageSelected(int i) {
        mCurrentItem = i;
        /*
        int pageIndex = i;
        if (i == 0) {
            pageIndex = mViewArrayList.size() - 2;
        } else if (i == mViewArrayList.size() - 1) {
            pageIndex = 1;
        }
        if (i != pageIndex) {
            mMainViewPager.setCurrentItem(pageIndex, false);
            return;
        }
        */
        //mMainViewPager.setCurrentItem(i, false);

        Log.d(TAG, "selected..  " + i);

    }

    @Override
    public void onPageScrollStateChanged(int i) {
        switch (i) {
            case ViewPager.SCROLL_STATE_IDLE:
                if(mCurrentItem == 0){
                    mMainViewPager.setCurrentItem(mViewArrayList.size() - 2, false);

                }else if (mCurrentItem == mViewArrayList.size() - 1){
                    mMainViewPager.setCurrentItem(1);
                }
                Log.d(TAG, "SCROLL_STATE_IDLE: i " + mMainViewPager.getCurrentItem());
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                if(mCurrentItem == 0){
                    mMainViewPager.setCurrentItem(mViewArrayList.size() - 2, false);

                }else if (mCurrentItem == mViewArrayList.size() - 1){
                    mMainViewPager.setCurrentItem(1);
                }
                break;
            case ViewPager.SCROLL_STATE_SETTLING:

                break;

        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "keycode: " + keyCode);

        switch (keyCode){
            case KeyEvent.KEYCODE_ENTER:
                startActivityFromPosition(mCurrentItem);
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"touch event" + event);

        return super.onTouchEvent(event);
    }

    private void initActivityClassNameList(){
        mActivityClassNameList.add(new AppClassName("contact", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui.activity.contacts.ContactMainActivity"));
        mActivityClassNameList.add(new AppClassName("call", "com.ctyon.ctyonlauncher", " "));
        mActivityClassNameList.add(new AppClassName("sms", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui.activity.message.MessageMainActivity"));
        mActivityClassNameList.add(new AppClassName("media", "com.android.ctyon.copyhome", "com.android.ctyon.copyhome.ui.MediaActivity"));
        mActivityClassNameList.add(new AppClassName("tools", "com.android.ctyon.copyhome", "com.android.ctyon.copyhome.ui.ToolsActivity"));
        mActivityClassNameList.add(new AppClassName("settings", "com.android.settings", "com.android.settings.Settings"));
        mActivityClassNameList.add(new AppClassName("wechat", "com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
        mActivityClassNameList.add(new AppClassName("yzf", "com.chinatelecom.bestpayclient", "com.chinatelecom.bestpayclient.ui.activity.GuideActivity"));
        mActivityClassNameList.add(new AppClassName("others", "com.android.ctyon.copyhome", "com.android.ctyon.copyhome.ui.OtherAppsActivity"));
    }

    private void initTitleList(){
        titleList = new LinkedList<String>();
        titleList.add("通讯录");
        titleList.add("通话管理");
        titleList.add("短信");
        titleList.add("多媒体");
        titleList.add("工具箱");
        titleList.add("设置");
        titleList.add("微信");
        titleList.add("翼支付");
        titleList.add("其他应用");

        View v = (View)findViewById(R.id.main2_bottom_layout);
        TextView tvOK = v.findViewById(R.id.ok_tv);
        TextView tvBack = v.findViewById(R.id.back_tv);
        tvOK.setTextColor(Color.rgb(255, 255, 255));
        tvBack.setTextColor(Color.rgb(255, 255, 255));
    }
    private void startActivityFromPosition(int p){

        p = mCurrentItem;
        if(0 == p){
          p = mViewArrayList.size() - 2;
        }else if (mViewArrayList.size() - 1 == p){
            p = 1;
        }
        //for(int i = 0; i < mActivityClassNameList.size(); i++){
            Log.d(TAG, "p: " + p + " packagename: " + mActivityClassNameList.get(p -1 ).getPackageName() + "class name: " + mActivityClassNameList.get(p - 1).getClassName());
        //}
        //Log.d(TAG, "p: " + p + " packagename: " + mActivityClassNameList.get(p ).getPackageName() + "class name: " + mActivityClassNameList.get(p ).getClassName());
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(mActivityClassNameList.get(p - 1).getPackageName(), mActivityClassNameList.get(p - 1).getClassName());
        intent.setComponent(componentName);
        startActivity(intent);
    }
}
