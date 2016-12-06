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
import javax.inject.Named;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import cc.easyandroid.easymvp.PresenterLoader;
import cc.easyandroid.easymvp.call.EasyThreadCall;

@PerActivity
public class ImageDetailsPresenter extends Presenter {


    protected QfangEasyWorkPresenter<ResponseInfo<GroupImageInfo>> presenter = new QfangEasyWorkPresenter<>();//使用clean
    protected QfangEasyWorkPresenter<File> presenter_down = new QfangEasyWorkPresenter<>();
    protected QfangEasyWorkPresenter<File> presenter_share = new QfangEasyWorkPresenter<>();

    ImageApi imageApi;
    int imageid;

    @Inject
    public ImageDetailsPresenter(ImageApi imageApi, int imageid ) {
        this.imageApi=imageApi;
        this.imageid=imageid;
        presenter.attachView(view);
        presenter_down.attachView(view_download);
        presenter_share.attachView(view_share);
    }

    @Override
    public void exeDownloadRequest(final FutureTarget<File> future) {
        presenter_down.execute(new EasyWorkUseCase.RequestValues<>("", new EasyThreadCall<>(new PresenterLoader<File>() {
            @Override
            public File loadInBackground() throws Exception {
                return down(future);
            }
        }), ""));
    }

    //banner_viewpager
    private File down(FutureTarget<File> future) throws ExecutionException, InterruptedException, IOException {
//        FutureTarget<File> future = Glide.with((Context) null).load(imageUrl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
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
    public void exeShare(final FutureTarget<File> future) {
        presenter_share.execute(new EasyWorkUseCase.RequestValues<>("", new EasyThreadCall<>(new PresenterLoader<File>() {
            @Override
            public File loadInBackground() throws Exception {
                return down(future);
            }
        }), ""));
    }

    @Override
    public void exeImageDetailsDataRequest() {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.queryGroupImageInfoDetails(imageid));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        presenter.execute(requestValues);
    }

    protected EasyWorkContract.View<ResponseInfo<GroupImageInfo>> view = new EasyWorkContract.View<ResponseInfo<GroupImageInfo>>() {
        @Override
        public void onStart(Object o) {
            if (getView() != null) {
                getView().onStart(o);
            }
        }

        @Override
        public void onError(Object o, Throwable throwable) {
            if (getView() != null) {
                getView().onError(o, throwable);
            }
        }

        @Override
        public void onSuccess(Object o, ResponseInfo<GroupImageInfo> groupImageInfoResponseInfo) {
            if (getView() != null) {
                getView().onSuccess(o, groupImageInfoResponseInfo);
            }
        }
    };

    protected EasyWorkContract.View<File> view_download = new EasyWorkContract.View<File>() {
        @Override
        public void onStart(Object o) {
            if (getView() != null)
                getView().onDownLoadRequestStart(o);

        }

        @Override
        public void onError(Object o, Throwable throwable) {
            if (getView() != null)
                getView().onDownloadError(o, throwable);
        }

        @Override
        public void onSuccess(Object o, File file) {
            if (getView() != null)
                getView().onDownloadSuccess(file);
        }
    };
    protected EasyWorkContract.View<File> view_share = new EasyWorkContract.View<File>() {
        @Override
        public void onStart(Object o) {

        }

        @Override
        public void onError(Object o, Throwable throwable) {
            if (getView() != null)
                getView().onShareError(o, throwable);
        }

        @Override
        public void onSuccess(Object o, File file) {
            if (getView() != null)
                getView().onShare(o, file);
        }
    };

    @Override
    protected void onDetachView() {
        super.onDetachView();
        presenter.detachView();
        presenter_down.detachView();
        presenter_share.detachView();
    }
}
