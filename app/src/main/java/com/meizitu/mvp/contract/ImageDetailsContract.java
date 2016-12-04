package com.meizitu.mvp.contract;

import android.app.Activity;

import com.bumptech.glide.request.FutureTarget;
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

        void onDownLoadRequestStart(Object o);

        void onDownloadSuccess(File imageFile);

        void onDownloadError(Object o, Throwable throwable);

        void onShare(Object var1, File imageFile);

        void onShareError(Object var1, Throwable var2);

        void onStart(Object var1);

        void onError(Object var1, Throwable var2);

        void onSuccess(Object var1, ResponseInfo<GroupImageInfo> var2);

    }

    abstract class Presenter extends EasyBasePresenter<View> {

        public abstract void exeDownloadRequest(FutureTarget<File> future );

        public abstract void exeShare(FutureTarget<File> future );

        public abstract void exeImageDetailsDataRequest();

    }
}
