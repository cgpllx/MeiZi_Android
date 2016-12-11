package com.meizitu.mvp.contract;

import com.meizitu.mvp.presenter.SimpleWorkPresenter;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.ResponseInfo;

import java.util.List;

import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * Created by chenguoping on 16/12/11.
 */
public interface TabContract {
    interface View extends IEasyView {

        void onTabLoadStart(Object o);

        void onTabLoadError(Object o, Throwable t);

        void onTabLoadSuccess(Object o, ResponseInfo<List<Category>> responseInfo);

    }

    class Presenter  extends SimpleWorkPresenter<TabContract.View> {


    }
}
