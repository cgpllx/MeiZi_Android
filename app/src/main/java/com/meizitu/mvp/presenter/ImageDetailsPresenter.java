package com.meizitu.mvp.presenter;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.ImageDetailsContract.Presenter;
import com.meizitu.pojo.GroupImageInfo;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;
import com.meizitu.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import cc.easyandroid.easymvp.PresenterLoader;
import cc.easyandroid.easymvp.call.EasyThreadCall;

@PerActivity
public class ImageDetailsPresenter extends Presenter {


    final ImageApi imageApi;
    final int imageid;
    final Context context;

    @Inject
    public ImageDetailsPresenter(Context context, ImageApi imageApi, int imageid) {
        this.context = context;
        this.imageApi = imageApi;
        this.imageid = imageid;
    }

    @Override
    public void exeDownloadRequest(String imageurl) {
        final FutureTarget<File> future = Glide.with(context).load(imageurl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        EasyWorkUseCase.RequestValues<File> requestValues = new EasyWorkUseCase.RequestValues<>("", new EasyThreadCall<>(new PresenterLoader<File>() {
            @Override
            public File loadInBackground() throws Exception {
                return down(future);
            }
        }), "");
//        if (isViewAttached())
//            getView().onDownLoadRequestStart();
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<File>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<File> responseValue) {
                if (isViewAttached())
                    getView().onDownloadSuccess(responseValue.getData());
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached())
                    getView().onDownloadError(throwable);
            }
        });
    }

    private File down(FutureTarget<File> future) throws ExecutionException, InterruptedException, IOException {
        File cacheFile = future.get();
        if (cacheFile != null) {
            File targetFile = new File(FileUtil.getSdpath() + File.separator + "mm", cacheFile.getName() + ".jpg");
            if (!targetFile.exists()) {
                File parentFile = targetFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                targetFile.createNewFile();
            }
            FileUtil.copyFileToTargetFile(cacheFile, targetFile);
            return targetFile;
        }
        return null;
    }

    @Override
    public void exeShare(String imageurl) {//分享和下载是一样额，都必须先下载
        final FutureTarget<File> future = Glide.with(context).load(imageurl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        EasyWorkUseCase.RequestValues<File> requestValues = new EasyWorkUseCase.RequestValues<>("", new EasyThreadCall<>(new PresenterLoader<File>() {
            @Override
            public File loadInBackground() throws Exception {
                return down(future);
            }
        }), "");
//        if (isViewAttached())
//            getView().onDownLoadRequestStart();
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<File>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<File> responseValue) {
                if (isViewAttached())
                    getView().onShare(responseValue.getData());
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached())
                    getView().onShareError(throwable);
            }
        });

    }

    @Override
    public void execute() {
        EasyCall<ResponseInfo<GroupImageInfo>> easyCall = new RetrofitCallToEasyCall<>(imageApi.queryGroupImageInfoDetails(imageid));
        EasyWorkUseCase.RequestValues<ResponseInfo<GroupImageInfo>> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        if (isViewAttached()) {
            getView().onStart(requestValues.getTag());
        }
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo<GroupImageInfo>>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo<GroupImageInfo>> responseValue) {
                ResponseInfo<GroupImageInfo> responseInfo = responseValue.getData();
                if (isViewAttached()) {
                    getView().onSuccess(responseInfo);
                }

            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached()) {
                    getView().onError(throwable);
                }
            }
        });
    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
    }
}
