package com.android.ctyon.copyhome.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.echocloud.presenter.MainPresenterImpl;
import com.android.ctyon.copyhome.echocloud.view.MainFragment;
import com.android.ctyon.copyhome.fragment.VoiceFragment;

public class VoiceActivity extends AppCompatActivity implements VoiceFragment.OnFragmentInteractionListener {

    public static final String FRAGMENT_TAG = "fragment_tag";
    private static final String TAG = "VoiceActivity";
    private VoiceFragment mFragment;
    private boolean longPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_start);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Fragment fragment = VoiceFragment.newInstance("", "");
        //fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
        mFragment = (VoiceFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (mFragment == null) {
            mFragment = VoiceFragment.newInstance("", "");
            fragmentManager.beginTransaction().add(R.id.fragment_layout, mFragment, FRAGMENT_TAG).commit();
        }
        new MainPresenterImpl(mFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                event.startTracking();
                if (event.getRepeatCount() == 0) {
                    longPress = false;
                }else{
                    longPress = true;
                }

                return true;
            case KeyEvent.KEYCODE_BACK:
                if (isNetworkAvailable()) {
                    showDialog();
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if (longPress){
                    longPress = false;
                    if (isNetworkAvailable()){
                        mFragment.cloudStopRecord();
                        mFragment.stopRotate();
                    }
                    return true;
                }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                longPress = true;
                if (isNetworkAvailable()){
                    mFragment.cloudStartRecord();
                    mFragment.startRotate();
                }
                return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        builder.setTitle(R.string.alertdialog_exit_title)
                .setMessage(R.string.alertdialog_exit_title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        }).show();
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            Toast.makeText(this, "无可用网络！", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}
