package com.android.ctyon.copyhome;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.utils.SpHelper;

import java.util.Calendar;

public class MyService extends IntentService {
    private static final String TAG = "MyService";
    MediaPlayer mediaPlayer;
    public MyService() {
        super("MyService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        mediaPlayer = MediaPlayer.create(this, R.raw.speechtime);
        mediaPlayer.start();
        if(SpHelper.getBoolean(this, "speech_time", false)){
            Intent intent_alarm = new Intent("com.copyhome.alarm");
            sendBroadcast(intent_alarm);
        }
    }


    private void alarm(){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int beginTime = Integer.valueOf(SpHelper.getString(this, "et_time_settings_begin", "8"));
        int endTime = Integer.valueOf(SpHelper.getString(this, "et_time_settings_end", "18"));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, beginTime);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
