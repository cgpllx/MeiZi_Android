package com.meizitu.mvp.presenter;

import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.pojo.ResponseInfo;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;

/**
 * Created by chenguoping on 16/11/21.
 */
public class ImageListPresenter extends ImageListContract.Presenter {

    protected QfangEasyWorkPresenter<ResponseInfo> openpresenter = new QfangEasyWorkPresenter<>();//使用clean
    protected QfangEasyWorkPresenter<ResponseInfo> closepresenter = new QfangEasyWorkPresenter<>();//使用clean

    protected QfangEasyWorkPresenter<ResponseInfo> closeSinglePresenter = new QfangEasyWorkPresenter<>();//使用clea

    protected QfangEasyWorkPresenter<ResponseInfo> openSinglePresenter = new QfangEasyWorkPresenter<>();//使用clean

    public ImageListPresenter() {
        openpresenter.attachView(openView);
        closepresenter.attachView(closeView);
        closeSinglePresenter.attachView(closeView);
        openSinglePresenter.attachView(closeView);

    }

    @Override
    public void exeCloseRequest(EasyWorkUseCase.RequestValues requestValues) {
        closepresenter.execute(requestValues);
    }

    @Override
    public void exeOpenRequest(EasyWorkUseCase.RequestValues requestValues) {
        openpresenter.execute(requestValues);
    }

    private final EasyWorkContract.View<ResponseInfo> openView = new EasyWorkContract.View<ResponseInfo>() {
        @Override
        public void onStart(Object o) {
            if (getView() != null) {
                getView().onOpenStart(o);
            }
        }

        @Override
        public void onError(Object o, Throwable throwable) {
            if (getView() != null) {
                getView().onOpenError(o, throwable);
            }
        }

        @Override
        public void onSuccess(Object o, ResponseInfo responseInfo) {
            if (getView() != null) {
                getView().onOpenSuccess(o, responseInfo);
            }
        }
    };

    private final EasyWorkContract.View<ResponseInfo> closeView = new EasyWorkContract.View<ResponseInfo>() {
        @Override
        public void onStart(Object o) {
            if (getView() != null) {
                getView().onCloseStart(o);
            }
        }

        @Override
        public void onError(Object o, Throwable throwable) {
            if (getView() != null) {
                getView().onCloseError(o, throwable);
            }
        }

        @Override
        public void onSuccess(Object o, ResponseInfo responseInfo) {
            if (getView() != null) {
                getView().onCloseSuccess(o, responseInfo);
            }
        }
    };

    @Override
    protected void onDetachView() {
        super.onDetachView();
        openpresenter.detachView();
        closepresenter.detachView();
    }
}
