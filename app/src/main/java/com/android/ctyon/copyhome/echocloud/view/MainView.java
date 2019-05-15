package com.android.ctyon.copyhome.echocloud.view;

import com.android.ctyon.copyhome.echocloud.presenter.MainPresenter;


public interface MainView extends BaseView<MainPresenter>{

    void showSendMessage(String msg);

    void showResultMessage(String msg);

    void resetText();

    void startRotate();

    void stopRotate();

}
