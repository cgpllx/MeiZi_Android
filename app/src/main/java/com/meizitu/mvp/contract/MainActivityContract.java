package com.meizitu.mvp.contract;

import android.app.Activity;

import com.meizitu.pojo.ADInfo;

import cc.easyandroid.easyclean.presentation.presenter.base.EasyIPresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;


public interface MainActivityContract {
    interface View extends IEasyView {
        void onAdInfoSuccess(ADInfo adInfo);
    }

    interface Presenter extends EasyIPresenter<View> {

        void favourableComment(Activity activity);

        void share(Activity activity);

        void feedback(Activity activity);

        void executeAdInfoRequest();
    }
}
