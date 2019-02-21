package com.android.ctyon.copyhome;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.ctyon.copyhome.utils.SpHelper;

import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    private static final int INTERVAL = 1000 * 60 * 60 * 1;// 1h
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        /*
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int minute = Calendar.getInstance().get(Calendar.MINUTE);
            Log.d(TAG, "hour " + hour + "min " + minute);
            if((hour >= Integer.valueOf(SpHelper.getString(context, "et_time_settings_begin", "8")))
                    && (hour <= Integer.valueOf(SpHelper.getString(context, "et_time_settings_end", "18")))
                    && minute == 0){
                Intent intentService = new Intent(context, MyService.class);
                context.startService(intentService);
            }
        }
        */

        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")
        || intent.getAction().equals("com.copyhome.alarm")){
            Log.d(TAG, "onReceive: boot_completed");
            if(SpHelper.getBoolean(context, "speech_time", false)){
                alarm(context);
            }

        }
    }

    private void alarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        int beginTime = Integer.valueOf(SpHelper.getString(context, "et_time_settings_begin", "8"));
        int endTime = Integer.valueOf(SpHelper.getString(context, "et_time_settings_end", "18"));
        Calendar cal = Calendar.getInstance();
        Log.d(TAG, "millis: " + cal.getTimeInMillis() + " begin " + beginTime + " end " + endTime + " cur_hour " + cal.get(Calendar.HOUR_OF_DAY)
                + " " + cal.get(Calendar.YEAR)+ " " + cal.get(Calendar.MONTH)+ " " + " " + cal.get(Calendar.DAY_OF_MONTH));
        if(cal.get(Calendar.HOUR_OF_DAY) >= beginTime && cal.get(Calendar.HOUR_OF_DAY) <= endTime){
            cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+ 1);
            //cal.set(Calendar.HOUR_OF_DAY, beginTime);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Log.d(TAG, Calendar.YEAR + Calendar.MONTH + " " +  Calendar.DAY_OF_MONTH);
            Intent intent = new Intent(context, MyService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), INTERVAL, pendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            Log.d(TAG, "millismillis " + cal.getTimeInMillis());
        }
        cal.set(Calendar.HOUR_OF_DAY, beginTime);



    }
}
