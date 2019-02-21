package com.android.ctyon.copyhome.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.adapter.ListviewAdapter;
import com.android.ctyon.copyhome.adapter.data.AppClassName;

import java.util.LinkedList;

public class AppListActivity extends AppCompatActivity {
    private static final String TAG = "AppListActivity";
    private Context mContext;
    private ListView mListView;
    private LinkedList<AppClassName> mData;
    private ListviewAdapter mListviewAdapter;
    private String mPrefKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        mPrefKey = getIntent().getStringExtra("preference_key");
        Log.d(TAG, "onCreate: pref key: " + mPrefKey);
        initAdapterData();
        mContext = AppListActivity.this;
        mListView = findViewById(R.id.applist_list_view);
        mListviewAdapter = new ListviewAdapter(mData, mContext);
        mListView.setAdapter(mListviewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: " + i);
                Intent intent = new Intent();
                intent.putExtra("pref_key", mPrefKey);
                intent.putExtra("name", mData.get(i).getName());
                intent.putExtra("position", i);
                setResult(2, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_MENU:
                int curPos = mListView.getSelectedItemPosition();
                Intent intent = new Intent();
                intent.putExtra("pref_key", mPrefKey);
                intent.putExtra("name", mData.get(curPos).getName());
                intent.putExtra("position", curPos);
                setResult(2, intent);
                finish();
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    private void initAdapterData(){
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
        mData.add(new AppClassName("日历", "com.calendar2345", "com.calendar2345.activity.CalendarMainActivity"));
        mData.add(new AppClassName("FM电台", "com.android.fmradio", "com.android.fmradio.FmMainActivity"));
        mData.add(new AppClassName("SoS紧急呼叫", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
        mData.add(new AppClassName("录音机", "com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder"));
        mData.add(new AppClassName("文件管理", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
        mData.add(new AppClassName("亲情号码", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
        mData.add(new AppClassName("计算器", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui" +
                ".activity.caculator.SimpleCalculatorActivity"));
        mData.add(new AppClassName("短信", "com.ctyon.ctyonlauncher", "com.ctyon" +
                ".ctyonlauncher.ui.activity.message.MessageMainActivity"));
        mData.add(new AppClassName("翼支付", "com.chinatelecom.bestpayclient", "com" +
                ".chinatelecom.bestpayclient.ui.activity.GuideActivity"));
        mData.add(new AppClassName("一键清除", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
    }
}
