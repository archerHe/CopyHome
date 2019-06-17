package com.android.ctyon.copyhome.ui;

import android.content.ComponentName;
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
import java.util.List;

public class MediaActivity extends AppCompatActivity {
    private static final String             TAG              = "MediaActivity";
    private              Context            mContext;
    private              List<AppClassName> mData            = null;
    private              ListviewAdapter    mListviewAdapter = null;
    private              ListView           mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_list_layout);

        mContext = MediaActivity.this;
        initAdapterData();
        mListView = (ListView) findViewById(R.id.media_list_view);
        mListviewAdapter = new ListviewAdapter((LinkedList<AppClassName>)mData, mContext);
        mListView.setAdapter(mListviewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "position: " + position);
                startApp(position);
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
                break;
        }


        return super.onKeyDown(keyCode, event);
    }

    private void initAdapterData(){
        mData = new LinkedList<AppClassName>();
        mData.add(new AppClassName("拍照", "org.codeaurora.snapcam", "com.android.camera.CameraLauncher"));
        mData.add(new AppClassName("相册", "com.android.gallery3d", "com.android.gallery3d.app.GalleryActivity"));
        mData.add(new AppClassName("音乐", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui.activity.music.MusicActivity"));
        mData.add(new AppClassName("收音机", "com.android.fmradio", "com.android.fmradio.FmMainActivity"));
        mData.add(new AppClassName("录音机", "com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder"));
        mData.add(new AppClassName("文件管理", "com.yarin.android.FileManager", "com.ctyon.FileManager.FirstAct"));

    }

    private void startApp(int p){
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(mData.get(p).getPackageName(), mData.get(p).getClassName());
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
