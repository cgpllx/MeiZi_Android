package com.meizitu.mvp.contract;

import android.app.Activity;
import android.view.MenuItem;

import cc.easyandroid.easyclean.presentation.presenter.base.EasyIPresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;


public interface MainActivityContract {
    interface View extends IEasyView {
    }

    interface Presenter extends EasyIPresenter<View> {
        void handleNavigationItemSelected(MenuItem item, Activity activity);
    }
}