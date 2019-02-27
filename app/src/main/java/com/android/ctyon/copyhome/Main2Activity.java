package com.android.ctyon.copyhome;

import com.android.ctyon.copyhome.adapter.data.AppClassName;
import com.android.ctyon.copyhome.ui.MainViewPager;


import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ctyon.copyhome.adapter.MainPagerAdapter;
import com.android.ctyon.copyhome.ui.MainViewPager;
import com.android.ctyon.copyhome.utils.SpHelper;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "Main2Activity";

    private MainViewPager            mMainViewPager;
    private MainPagerAdapter         mMainPagerAdapter;
    private ArrayList<View>          mViewArrayList;
    private LinkedList<String>       titleList;
    private Handler                  mHandler;
    private TextView                 tv_title;
    private LinkedList<AppClassName> mActivityClassNameList;
    private LinearLayout             mLayout;
    private List<String>                   mTitleList;
    private int[]                    mMipmaps;
    private int                      mCurrentItem;
    private TextToSpeech             mTextToSpeech;

    private boolean isSpeechMenuOn;

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
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {

                }
                super.handleMessage(msg);
            }
        };
        mTextToSpeech = MyApplication.getInstance().mTextToSpeech;


        mLayout = findViewById(R.id.main2_layout);
        tv_title = findViewById(R.id.title_tv);

        //mHandler.postDelayed(mRunnable, 3000);
    /*
        mMainViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.android.ctyon.copyhome",
                "com.android.ctyon.copyhome.ui.OtherAppsActivity");
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
        Log.d(TAG, "onPageSelected: ");
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

        // Log.d(TAG, "selected..  " + i);


    }

    @Override
    public void onPageScrollStateChanged(int i) {
        switch (i) {
            case ViewPager.SCROLL_STATE_IDLE:
                Log.d(TAG, "current item : " + mCurrentItem);
                if (mCurrentItem == 0) {
                    mMainViewPager.setCurrentItem(mViewArrayList.size() - 2, false);
                    mLayout.setBackgroundResource(mMipmaps[mViewArrayList.size() - 2]);
                    tv_title.setText(mTitleList.get(mViewArrayList.size() - 2));


                } else if (mCurrentItem == mViewArrayList.size() - 1) {
                    mMainViewPager.setCurrentItem(1);
                    mLayout.setBackgroundResource(mMipmaps[1]);
                    tv_title.setText(mTitleList.get(1));
                }else{
                    mLayout.setBackgroundResource(mMipmaps[mCurrentItem]);
                    tv_title.setText(mTitleList.get(mCurrentItem));
                    //Log.d(TAG, "title: " + mTitleList[mCurrentItem]);
                }

                if(isSpeechMenuOn){
                    mTextToSpeech.speak(titleList.get(mCurrentItem - 1), TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                if (mCurrentItem == 0) {
                    mMainViewPager.setCurrentItem(mViewArrayList.size() - 2, false);

                } else if (mCurrentItem == mViewArrayList.size() - 1) {
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

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                sendKeyCode2(KeyEvent.KEYCODE_DPAD_RIGHT);
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                sendKeyCode2(KeyEvent.KEYCODE_DPAD_LEFT);
                break;
            case KeyEvent.KEYCODE_MENU:
            case KeyEvent.KEYCODE_ENTER:
                startActivityFromPosition(mCurrentItem);
                break;
                /*
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.android.ctyon.copyhome",
                        "com.android.ctyon.copyhome.ui.MainLauncher");
                intent.setComponent(componentName);
                startActivity(intent);
                finish();
                break;
                */
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
                break;

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "touch event" + event);

        return super.onTouchEvent(event);
    }


    private void initActivityClassNameList() {
        mActivityClassNameList.add(new AppClassName("contact", "com.ctyon.ctyonlauncher", "com" +
                ".ctyon.ctyonlauncher.ui.activity.contacts.ContactMainActivity"));
        mActivityClassNameList.add(new AppClassName("call", "com.ctyon.ctyonlauncher", "com.ctyon" +
                ".ctyonlauncher.ui.activity.dialer.CallMainActivity"));
        mActivityClassNameList.add(new AppClassName("sms", "com.ctyon.ctyonlauncher", "com.ctyon" +
                ".ctyonlauncher.ui.activity.message.MessageMainActivity"));
        mActivityClassNameList.add(new AppClassName("media", "com.android.ctyon.copyhome", "com" +
                ".android.ctyon.copyhome.ui.MediaActivity"));
        mActivityClassNameList.add(new AppClassName("tools", "com.android.ctyon.copyhome", "com" +
                ".android.ctyon.copyhome.ui.ToolsActivity"));
        mActivityClassNameList.add(new AppClassName("settings", "com.android.settings", "com" +
                ".android.settings.Settings"));
        mActivityClassNameList.add(new AppClassName("wechat", "com.tencent.mm", "com.tencent.mm" +
                ".ui.LauncherUI"));
        mActivityClassNameList.add(new AppClassName("yzf", "com.chinatelecom.bestpayclient", "com" +
                ".chinatelecom.bestpayclient.ui.activity.GuideActivity"));
        mActivityClassNameList.add(new AppClassName("others", "com.android.ctyon.copyhome", "com" +
                ".android.ctyon.copyhome.ui.OtherAppsActivity"));
    }

    private void initTitleList() {

        mMipmaps = new int[11];
        mMipmaps[0] = R.mipmap.others;
        mMipmaps[1] = R.mipmap.contact;
        mMipmaps[2] = R.mipmap.call;
        mMipmaps[3] = R.mipmap.sms;
        mMipmaps[4] = R.mipmap.media;
        mMipmaps[5] = R.mipmap.tools;
        mMipmaps[6] = R.mipmap.settings;
        mMipmaps[7] = R.mipmap.weixin;
        mMipmaps[8] = R.mipmap.yzf;
        mMipmaps[9] = R.mipmap.others;
        mMipmaps[10] = R.mipmap.contact;

        mTitleList = new LinkedList<>();
        mTitleList.add("其他应用");
        mTitleList.add("通讯录");
        mTitleList.add("通话管理");
        mTitleList.add("短信");
        mTitleList.add("多媒体");
        mTitleList.add("工具箱");
        mTitleList.add("设置");
        mTitleList.add("微信");
        mTitleList.add("翼支付");
        mTitleList.add("其他应用");
        mTitleList.add("通讯录");

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

        View v = (View) findViewById(R.id.main2_bottom_layout);
        TextView tvOK = v.findViewById(R.id.ok_tv);
        TextView tvBack = v.findViewById(R.id.back_tv);
        tvOK.setTextColor(Color.rgb(255, 255, 255));
        tvBack.setTextColor(Color.rgb(255, 255, 255));


    }

    private void startActivityFromPosition(int p) {

        p = mCurrentItem;
        if (0 == p) {
            p = mViewArrayList.size() - 2;
        } else if (mViewArrayList.size() - 1 == p) {
            p = 1;
        }
        //for(int i = 0; i < mActivityClassNameList.size(); i++){
        Log.d(TAG, "p: " + p + " packagename: " + mActivityClassNameList.get(p - 1)
                .getPackageName() + "class name: " + mActivityClassNameList.get(p - 1)
                .getClassName());
        //}
        //Log.d(TAG, "p: " + p + " packagename: " + mActivityClassNameList.get(p ).getPackageName
        // () + "class name: " + mActivityClassNameList.get(p ).getClassName());
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(mActivityClassNameList.get(p - 1)
                .getPackageName(), mActivityClassNameList.get(p - 1).getClassName());
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

    private void sendKeyCode2(final int keyCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 创建一个Instrumentation对象9
                    Instrumentation inst = new Instrumentation();
                    // 调用inst对象的按键模拟方法
                    inst.sendKeyDownUpSync(keyCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isSpeechMenuOn = SpHelper.getBoolean(Main2Activity.this, "voice_menu", true);
        if (isSpeechMenuOn) {
            mTextToSpeech.speak(titleList.get(mCurrentItem - 1), TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
