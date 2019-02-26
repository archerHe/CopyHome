package com.android.ctyon.copyhome;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.utils.SpHelper;

import java.util.Calendar;

public class MyService extends IntentService {
    private static final String TAG = "MyService";
    private MediaPlayer mediaPlayer;
    private TextToSpeech mTextToSpeech;

    private static final int MSG_PLAY_TIME = 10;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_PLAY_TIME:
                    playTime();
                    break;
            }
        }
    };

    public MyService() {
        super("MyService");

    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        mediaPlayer = MediaPlayer.create(this, R.raw.speechtime);
        mediaPlayer.start();
        mHandler.sendEmptyMessageDelayed(MSG_PLAY_TIME, 4 * 1000);


        if(SpHelper.getBoolean(this, "speech_time", false)){
            Intent intent_alarm = new Intent("com.copyhome.alarm");
            sendBroadcast(intent_alarm);
        }


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.d(TAG, "onCompletion");
            }
        });

    }


    private void alarm(){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int beginTime = Integer.valueOf(SpHelper.getString(this, "et_time_settings_begin", "8"));
        int endTime = Integer.valueOf(SpHelper.getString(this, "et_time_settings_end", "18"));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, beginTime);

    }

    private void playTime(){
        mTextToSpeech = MyApplication.getInstance().mTextToSpeech;
        Calendar c = Calendar.getInstance();
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        mTextToSpeech.speak(hour + "点", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
