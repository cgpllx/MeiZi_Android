package com.meizitu.mvp.contract;

import android.app.Activity;

import com.bumptech.glide.request.FutureTarget;
import com.meizitu.mvp.presenter.SimpleWorkPresenter;
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.pojo.ResponseInfo;

import java.io.File;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyclean.presentation.presenter.base.EasyBasePresenter;
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

    abstract class Presenter extends SimpleWorkPresenter<View> {

        public abstract void exeDownloadRequest(String imageUrl);

        public abstract void exeShare(String imageUrl);

    }
}
