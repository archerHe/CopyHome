package com.android.ctyon.copyhome.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


    private void initAdapterData(){
        mData = new LinkedList<>();
        mData.add(new AppClassName("拍照", "org.codeaurora.snapcam", "com.android.camera.CameraLauncher"));
        mData.add(new AppClassName("相册", "com.android.gallery3d", "com.android.gallery3d.app.GalleryActivity"));
        mData.add(new AppClassName("音乐", "com.android.music", "com.android.music.MusicBrowserActivity"));
        mData.add(new AppClassName("收音机", "com.android.fmradio", "com.android.fmradio.FmMainActivity"));
        mData.add(new AppClassName("录音机", "com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder"));
        mData.add(new AppClassName("文件管理", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));
    }
}
