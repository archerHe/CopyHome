package com.android.ctyon.copyhome.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpHelper {
    private static SharedPreferences mIntance;
    private static final String TAG = "com.android.ctyon.copyhome_preferences";

    private static SharedPreferences getIntance(Context context){
        if(null == mIntance){
            mIntance = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        }
        return mIntance;
    }

    public static int getInt(Context context, String key, int defaultVal){
        SharedPreferences sp = getIntance(context);
        return sp.getInt(key, defaultVal);
    }

    public static void putInt(Context context, String key, int val){
        SharedPreferences sp = getIntance(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultVal){
        SharedPreferences sp = getIntance(context);
        return sp.getBoolean(key, defaultVal);
    }
}
