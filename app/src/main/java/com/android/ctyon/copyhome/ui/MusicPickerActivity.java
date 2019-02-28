package com.android.ctyon.copyhome.ui;

import android.app.Dialog;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.utils.FilePathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import  static com.android.ctyon.copyhome.utils.FilePathUtil.scanFileList;

public class MusicPickerActivity extends AppCompatActivity {
    private static final String TAG = "MusicPickerActivity";
    private TextView     tv_title;
    private ListView     mListView;
    private ArrayAdapter<String> mListviewAdapter;
    List<String> uniqueNameList;
    List<String> uniquePathList;

    private Dialog          dialog;
    private ExecutorService executorService;

    private static final int MSG_UPDATE_LISTVIEW = 10;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE_LISTVIEW:
                    updateListView();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tools_list_layout);
        tv_title = findViewById(R.id.tools_title_tv);
        tv_title.setText("选择音乐曲目");
        mListView = findViewById(R.id.tools_list_view);
        loadMusicList();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private  void updateListView(){
        mListView.setAdapter(mListviewAdapter);
    }

    private void loadMusicList() {
        /*
        if(dialog != null)
            dialog.close();
        dialog = new LoadingDialog(this);
        dialog.setInterceptBack(intercept_back_event)
                .setLoadingText("拼命加载中...")
                .setLoadStyle(style)
                .show();
        */
        Log.d(TAG, "loadMusicList: ");
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                Log.d(TAG, "run: ");
                //外置置sd卡路径
                List<String> extraList = new ArrayList<String>();
                String storagePath = FilePathUtil.getStoragePath(MusicPickerActivity.this, true);
                if(storagePath != null){
                    extraList = scanFileList(new File(storagePath));
                    Log.d(TAG, "extralist: " + extraList);
                }
                File externalStorageDirectory = Environment.getExternalStorageDirectory();

                List<String> names = scanFileList(externalStorageDirectory);

                HashSet<String> hsNames = new HashSet<>(names);

                Log.d(TAG, "hsNames: " + hsNames);
                uniquePathList = new ArrayList<>();
                uniqueNameList = new ArrayList<>();
                uniquePathList.addAll(hsNames);
                uniqueNameList = getFileNameFromPath(uniquePathList);
                names.clear();
                names = null;
                hsNames.clear();
                hsNames = null;
                mListviewAdapter = new ArrayAdapter<String>(MusicPickerActivity.this, android.R.layout.simple_list_item_single_choice, uniqueNameList);
                mHandler.sendEmptyMessage(MSG_UPDATE_LISTVIEW);


            }
        });

    }

    private List getFileNameFromPath(List<String> list){
        List<String> namesList = new ArrayList<>();
        for (String path : list
             ) {
            File file = new File(path);
            namesList.add(file.getName());
        }
        return namesList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(executorService != null){
            executorService.shutdownNow();
        }
        Log.d(TAG, "onDestroy: ");
    }
}
