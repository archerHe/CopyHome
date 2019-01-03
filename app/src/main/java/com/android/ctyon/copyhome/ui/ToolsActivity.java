package com.android.ctyon.copyhome.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        mListView = (ListView) findViewById(R.id.tools_list_view);
        mListviewAdapter = new ListviewAdapter((LinkedList<AppClassName>) mData, mContext);
        mListView.setAdapter(mListviewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 4:
                    startApp(4);
                        break;
                    case 5:
                    startApp(5);
                        break;
                    case 6:
                    startApp(6);
                        break;
                }
            }
        });
    }

    private void initAdapterData() {
        mData = new LinkedList<AppClassName>();
        mData.add(new AppClassName("sos", "packagename", "classname"));
        mData.add(new AppClassName("导航键启动设置", "packagename", "classname"));
        mData.add(new AppClassName("亲情号码", "packagename", "classname"));
        mData.add(new AppClassName("语音助手", "packagename", "classname"));
        mData.add(new AppClassName("计算器", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui" +
                ".activity.caculator.SimpleCalculatorActivity"));
        mData.add(new AppClassName("时钟", "packagename", "classname"));
        mData.add(new AppClassName("日历", "com.ctyon.ctyonlauncher", "com.ctyon.ctyonlauncher.ui" +
                ".activity.calendar.CalendarActivity"));
        mData.add(new AppClassName("一键清理", "packagename", "classname"));
    }

    private void startApp(int p){
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(mData.get(p).getPackageName(), mData.get(p).getClassName());
        intent.setComponent(componentName);
        startActivity(intent);
    }
}
