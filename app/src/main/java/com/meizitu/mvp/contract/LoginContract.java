package com.meizitu.mvp.contract;

import com.meizitu.mvp.presenter.SimpleWorkPresenter;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.ResponseInfo;

import java.util.List;

import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * Created by chenguoping on 16/12/11.
 */
public interface LoginContract {
    interface View extends IEasyView {

        void onLoginStart(Object o);

        void onLoginError(Object o, Throwable t);

        void onLoginSuccess(Object o, ResponseInfo responseInfo);

    }

    class Presenter extends SimpleWorkPresenter<LoginContract.View> {


    }
}
