package com.meizitu.mvp.contract;

import android.app.Activity;
import android.view.MenuItem;

import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.pojo.ResponseInfo;

import cc.easyandroid.easyclean.presentation.presenter.base.EasyIPresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * 屋苑menu的Contract
 */
public interface ImageDetailsContract {
    interface View extends IEasyView {

        void onStart(Object tag);

        void onError(Throwable var2);

        void onSuccess(ResponseInfo<GroupImageInfo> var2);

    }

    interface Presenter extends EasyIPresenter<View> {
        void handleNavigationItemSelected(MenuItem item, Activity activity,String imageUrl);

        void initFavoriteMenu(View actionView);
    }
}
