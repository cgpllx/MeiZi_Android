package com.meizitu.mvp.presenter;

import android.os.Bundle;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.items.Item_GroupImageInfoListItem;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;

import javax.inject.Inject;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;

/**
 * Created by chenguoping on 16/11/21.
 */
@PerActivity
public class ImageListPresenter extends ImageListContract.Presenter {

    protected QfangEasyWorkPresenter<ResponseInfo> openpresenter = new QfangEasyWorkPresenter<>();//使用clean

    protected QfangEasyWorkPresenter<ResponseInfo> closepresenter = new QfangEasyWorkPresenter<>();//使用clean

    protected QfangEasyWorkPresenter<ResponseInfo> closeSinglePresenter = new QfangEasyWorkPresenter<>();//使用clea

    protected QfangEasyWorkPresenter<ResponseInfo> openSinglePresenter = new QfangEasyWorkPresenter<>();//使用clean

    int gategoryId;
    ImageApi imageApi;

    @Inject
    public ImageListPresenter(ImageApi imageApi, int gategoryId) {
        this.gategoryId = gategoryId;
        this.imageApi = imageApi;
        openpresenter.attachView(openView);
        closepresenter.attachView(closeView);
        closeSinglePresenter.attachView(closeSingleView);
        openSinglePresenter.attachView(openSingleView);
    }


    private final EasyWorkContract.View<ResponseInfo> openView = new EasyWorkContract.View<ResponseInfo>() {
        @Override
        public void onStart(Object o) {
            if (getView() != null) {
                getView().onOpenAllStart(o);
            }
        }

        @Override
        public void onError(Object o, Throwable throwable) {
            if (getView() != null) {
                getView().onOpenAllError(o, throwable);
            }
        }

        @Override
        public void onSuccess(Object o, ResponseInfo responseInfo) {
            if (getView() != null) {
                getView().onOpenAllSuccess(o, responseInfo);
            }
        }
    };

    private final EasyWorkContract.View<ResponseInfo> closeView = new EasyWorkContract.View<ResponseInfo>() {
        @Override
        public void onStart(Object o) {
            if (getView() != null) {
                getView().onCloseAllStart(o);
            }
        }

        @Override
        public void onError(Object o, Throwable throwable) {
            if (getView() != null) {
                getView().onCloseAllError(o, throwable);
            }
        }

        @Override
        public void onSuccess(Object o, ResponseInfo responseInfo) {
            if (getView() != null) {
                getView().onCloseAllSuccess(o, responseInfo);

            }
        }
    };
    private final EasyWorkContract.View<ResponseInfo> closeSingleView = new EasyWorkContract.View<ResponseInfo>() {
        @Override
        public void onStart(Object o) {
            if (getView() != null) {
                getView().onCloseSingleStart(o);
            }
        }

        @Override
        public void onError(Object o, Throwable throwable) {
            if (getView() != null) {
                getView().onCloseSingleError(o, throwable);
            }
        }

        @Override
        public void onSuccess(Object o, ResponseInfo responseInfo) {
            if (getView() != null) {
                getView().onCloseSingleSuccess((Item_GroupImageInfoListItem) o, responseInfo);
            }
        }
    };
    private final EasyWorkContract.View<ResponseInfo> openSingleView = new EasyWorkContract.View<ResponseInfo>() {
        @Override
        public void onStart(Object o) {
            if (getView() != null) {
                getView().onOpenSingleStart(o);
            }
        }

        @Override
        public void onError(Object o, Throwable throwable) {
            if (getView() != null) {
                getView().onOpenSingleError(o, throwable);
            }
        }

        @Override
        public void onSuccess(Object o, ResponseInfo responseInfo) {
            if (getView() != null) {
                getView().onOpenSingleSuccess((Item_GroupImageInfoListItem) o, responseInfo);
            }
        }
    };

    @Override
    protected void onDetachView() {
        super.onDetachView();
        openpresenter.detachView();
        closepresenter.detachView();
        closeSinglePresenter.detachView();
        openSinglePresenter.detachView();
    }


    @Override
    public void exeOpenAllRequest() {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.openGroupImageInfoByCategoryCode(gategoryId));
        openpresenter.execute(new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ONLY));
    }

    @Override
    public void exeCloseAllRequest() {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.closeGroupImageInfoByCategoryCode(gategoryId));
        closepresenter.execute(new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ONLY));
    }

    @Override
    public void exeOpenSingleRequest(Item_GroupImageInfoListItem item_groupImageInfoListItem) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.openGroupImageInfoById(item_groupImageInfoListItem.getId()));
        openSinglePresenter.execute(new EasyWorkUseCase.RequestValues<>(item_groupImageInfoListItem, easyCall, CacheMode.LOAD_NETWORK_ONLY));
    }

    @Override
    public void exeCloseSingleRequest(Item_GroupImageInfoListItem item_groupImageInfoListItem) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.closeGroupImageInfoById(item_groupImageInfoListItem.getId()));
        closeSinglePresenter.execute(new EasyWorkUseCase.RequestValues<>(item_groupImageInfoListItem, easyCall, CacheMode.LOAD_NETWORK_ONLY));
    }
}
