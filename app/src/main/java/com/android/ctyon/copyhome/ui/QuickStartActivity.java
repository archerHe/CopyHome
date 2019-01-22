package com.android.ctyon.copyhome.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.android.ctyon.copyhome.R;

public class QuickStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_quick_start);
        getFragmentManager().beginTransaction().replace(R.id.fragment_layout, new QuickStartFragment()).commit();
    }

}
