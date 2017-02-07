package com.meizitu.mvp.contract;

import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.pojo.ResponseInfo;

import java.io.File;

import cc.easyandroid.easyclean.presentation.presenter.base.EasyIPresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;

/**
 * 屋苑menu的Contract
 */
public interface ImageDetailsContract {
    interface View extends IEasyView {

        void onDownloadSuccess(File imageFile);

        void onDownloadError(Throwable throwable);

        void onShare(File imageFile);

        void onShareError(Throwable var2);

        void onStart(Object tag);

        void onError(Throwable var2);

        void onSuccess(ResponseInfo<GroupImageInfo> var2);

    }

    interface Presenter extends EasyIPresenter<View> {

        void exeDownloadRequest(String imageUrl);

        void exeShare(String imageUrl);

        void execute(String cacheControl);

    }
}
