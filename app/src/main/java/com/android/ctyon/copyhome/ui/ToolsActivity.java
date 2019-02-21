package com.android.ctyon.copyhome.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.adapter.ListviewAdapter;
import com.android.ctyon.copyhome.adapter.data.AppClassName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ToolsActivity extends AppCompatActivity {
    private static final String             TAG              = "ToolsActivity";
    private final String SHOW_RECENT_APPS_RECEIVER = "anroid.intent.show.recent.apps";

    private              Context            mContext;
    private              List<AppClassName> mData            = null;
    private              ListviewAdapter    mListviewAdapter = null;
    private              ListView           mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.tools_list_layout);

        mContext = ToolsActivity.this;
        initAdapterData();
        mListView = findViewById(R.id.tools_list_view);
        mListviewAdapter = new ListviewAdapter((LinkedList<AppClassName>) mData, mContext);
        mListView.setAdapter(mListviewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: pos " + position);
                switch (position) {
                    case 0:
                        Intent intent = new Intent("com.android.settings.SOS_SETTINGS");
                        intent.addCategory("android.intent.category.DEFAULT");
                        startActivity(intent);

                        break;
                    case 3:
                        startApp(3);
                        break;
                    case 1:
                        startApp(1);
                        break;
                    case 4:
                        startApp(4);
                        break;
                    case 5:
                        startApp(5);
                        break;
                    case 6:
                        startApp(6);
                        break;
                    case 7:
                        Intent intent1 = new Intent();
                        intent1.setAction(SHOW_RECENT_APPS_RECEIVER);
                        ToolsActivity.this.sendBroadcast(intent1);
                        break;
                    case KeyEvent.KEYCODE_MENU:
                        int curPos = mListView.getSelectedItemPosition();
                        startApp(curPos);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
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
            case KeyEvent.KEYCODE_MENU:
                int curPos = mListView.getSelectedItemPosition();
                startApp(curPos);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initAdapterData() {
        mData = new LinkedList<>();
        mData.add(new AppClassName("SOS求助", "com.android.gallery3d", "com.android.gallery3d.app.GalleryActivity"));
        mData.add(new AppClassName("导航键启动设置", "com.android.ctyon.copyhome", "com.android.ctyon" +
                ".copyhome.ui.QuickStartActivity"));
        mData.add(new AppClassName("亲情号码", "com.android.gallery3d", "com.android.gallery3d.app.GalleryActivity"));
        mData.add(new AppClassName("语音助手", "com.android.ctyon.copyhome", "com.android.ctyon.copyhome.ui.SpeechSettingActivity"));
        mData.add(new AppClassName("计算器", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui" +
                ".activity.caculator.SimpleCalculatorActivity"));
        mData.add(new AppClassName("时钟", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui.activity.alarm.AlarmClockActivity"));
        mData.add(new AppClassName("日历", "com.calendar2345", "com.calendar2345.activity.CalendarMainActivity"));
        mData.add(new AppClassName("一键清理", "com.android.gallery3d", "com.android.gallery3d.app.GalleryActivity"));
    }

    private void startApp(int p) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(mData.get(p).getPackageName(), mData.get
                (p).getClassName());
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
