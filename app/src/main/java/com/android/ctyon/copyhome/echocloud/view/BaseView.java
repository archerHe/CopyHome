package com.android.ctyon.copyhome.echocloud.view;

import com.android.ctyon.copyhome.echocloud.presenter.BasePresenter;

public interface BaseView<P extends BasePresenter> {

    void setPresenter(P presenter);

}
