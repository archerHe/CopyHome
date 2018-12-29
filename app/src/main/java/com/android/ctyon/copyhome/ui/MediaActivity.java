package com.android.ctyon.copyhome.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        mListView = (ListView)findViewById(R.id.media_list_view);
        mListviewAdapter = new ListviewAdapter((LinkedList<AppClassName>)mData, mContext);
        mListView.setAdapter(mListviewAdapter);
    }

    private void initAdapterData(){
        mData = new LinkedList<AppClassName>();
        mData.add(new AppClassName("拍照", "packagename", "classname"));
        mData.add(new AppClassName("相册", "packagename", "classname"));
        mData.add(new AppClassName("音乐", "packagename", "classname"));
        mData.add(new AppClassName("收音机", "packagename", "classname"));
        mData.add(new AppClassName("录音机", "packagename", "classname"));
        mData.add(new AppClassName("文件管理", "packagename", "classname"));

    }
}
