package com.android.ctyon.copyhome.echocloud.presenter;

import com.android.ctyon.copyhome.echocloud.model.Type;

public interface MainPresenter extends BasePresenter{

    void sendNormalMessage(Type type, String text);

    void sendStreamMessage(Type type, String text);

    void startRecord();

    void stopRecord();

    void playVoice();

    void stopVoice();

}
