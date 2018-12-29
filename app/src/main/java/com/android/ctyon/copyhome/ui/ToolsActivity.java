package com.android.ctyon.copyhome.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
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
    private static final  String TAG = "ToolsActivity";
    private Context mContext;
    private List<AppClassName> mData = null;
    private ListviewAdapter mListviewAdapter = null;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.tools_list_layout);

        mContext = ToolsActivity.this;
        initAdapterData();
        mListView = (ListView)findViewById(R.id.tools_list_view);
        mListviewAdapter = new ListviewAdapter((LinkedList<AppClassName>) mData, mContext);
        mListView.setAdapter(mListviewAdapter);
    }

    private void initAdapterData(){
        mData = new LinkedList<AppClassName>();
        mData.add(new AppClassName("sos", "packagename", "classname"));
        mData.add(new AppClassName("导航键启动设置", "packagename", "classname"));
        mData.add(new AppClassName("亲情号码", "packagename", "classname"));
        mData.add(new AppClassName("语音助手", "packagename", "classname"));
        mData.add(new AppClassName("计算器", "packagename", "classname"));
        mData.add(new AppClassName("时钟", "packagename", "classname"));
        mData.add(new AppClassName("日历", "packagename", "classname"));
        mData.add(new AppClassName("一键清理", "packagename", "classname"));
    }
}
