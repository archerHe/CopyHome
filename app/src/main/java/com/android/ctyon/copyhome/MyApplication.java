package com.android.ctyon.copyhome;

import android.app.Application;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class MyApplication extends Application {
    public         TextToSpeech  mTextToSpeech;
    private static MyApplication app;
    private static final String TAG = "MyApplication";

    public static MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initSpeak();
    }

    private void initSpeak() {
        if (null == mTextToSpeech) {
            mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    Log.d(TAG, "status: " + status);
                    if (status == TextToSpeech.SUCCESS) {
                        int result = mTextToSpeech.setLanguage(Locale.CHINA);
                        if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                            Toast.makeText(app, "not support china", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "not support china");

                        }else{
                            Toast.makeText(app, "support china", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "support china");

                        }
                    }
                }
            });
        }

    }


}
