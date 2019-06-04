package com.android.ctyon.copyhome.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    private static final String logFileName = "_exception.log";
    private static final String TAG = "CrashHandler";

    private CrashHandler(){

    }

    public static CrashHandler getCrashHandler(){
        return sInstance;
    }

    public void init(Context context){
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try{
            recordException(e);
        }catch(Exception e1){
            e1.printStackTrace();
        }
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(t, e);
        }else {
            Process.killProcess(Process.myPid());
        }
    }

    private void recordException(@NonNull Throwable e){
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + time + logFileName);
        Log.d(TAG, "exceptionFile: " + Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + time + logFileName);
        try{
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            e.printStackTrace(pw);
            pw.close();

        }catch (Exception e1){
            e1.printStackTrace();
        }
    }


}
