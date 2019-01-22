package com.android.ctyon.copyhome.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.android.ctyon.copyhome.R;

public class QuickStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.perference_set_activity);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_quick_start);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.quick_start_select_title);
        getFragmentManager().beginTransaction().replace(R.id.fragment_layout, new QuickStartFragment()).commit();
    }

}
