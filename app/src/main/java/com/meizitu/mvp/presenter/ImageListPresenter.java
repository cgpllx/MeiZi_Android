package com.meizitu.mvp.presenter;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.items.Item_GroupImageInfoListItem;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;

@PerActivity
public class ImageListPresenter extends ImageListContract.Presenter {

    final int gategoryId;
    final ImageApi imageApi;

    @Inject
    public ImageListPresenter(ImageApi imageApi, int gategoryId) {
        this.gategoryId = gategoryId;
        this.imageApi = imageApi;
    }


    @Override
    public void exeOpenAllRequest() {
        EasyCall<ResponseInfo> easyCall = new RetrofitCallToEasyCall<>(imageApi.openGroupImageInfoByCategoryCode(gategoryId));
        final EasyWorkUseCase.RequestValues<ResponseInfo> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ONLY);
        if (isViewAttached()) {
            getView().onOpenAllStart(requestValues.getTag());
        }
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo> responseValue) {
                if (isViewAttached()) {
                    getView().onOpenAllSuccess(responseValue.getTag(), responseValue.getData());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached()) {
                    getView().onOpenAllError(requestValues.getTag(), throwable);
                }
            }
        });
    }

    @Override
    public void exeCloseAllRequest() {
        EasyCall<ResponseInfo> easyCall = new RetrofitCallToEasyCall<>(imageApi.closeGroupImageInfoByCategoryCode(gategoryId));
        final EasyWorkUseCase.RequestValues<ResponseInfo> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ONLY);
        if (isViewAttached()) {
            getView().onCloseAllStart(requestValues.getTag());
        }
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo> responseValue) {
                if (isViewAttached()) {
                    getView().onCloseAllSuccess(responseValue.getTag(), responseValue.getData());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached()) {
                    getView().onCloseAllError(requestValues.getTag(), throwable);
                }
            }
        });
    }

    @Override
    public void exeOpenSingleRequest(final Item_GroupImageInfoListItem item_groupImageInfoListItem) {
        EasyCall<ResponseInfo> easyCall = new RetrofitCallToEasyCall<>(imageApi.openGroupImageInfoById(item_groupImageInfoListItem.getId()));
        final EasyWorkUseCase.RequestValues<ResponseInfo> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ONLY);
        if (isViewAttached()) {
            getView().onOpenSingleStart(requestValues.getTag());
        }
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo> responseValue) {
                if (isViewAttached()) {
                    getView().onOpenSingleSuccess(item_groupImageInfoListItem, responseValue.getData());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached()) {
                    getView().onOpenSingleError(requestValues.getTag(), throwable);
                }
            }
        });
    }

    @Override
    public void exeCloseSingleRequest(final Item_GroupImageInfoListItem item_groupImageInfoListItem) {
        EasyCall<ResponseInfo> easyCall = new RetrofitCallToEasyCall<>(imageApi.closeGroupImageInfoById(item_groupImageInfoListItem.getId()));
        final EasyWorkUseCase.RequestValues<ResponseInfo> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ONLY);
        if (isViewAttached()) {
            getView().onCloseSingleStart(requestValues.getTag());
        }
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo> responseValue) {
                if (isViewAttached()) {
                    getView().onCloseSingleSuccess(item_groupImageInfoListItem, responseValue.getData());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached()) {
                    getView().onCloseSingleError(requestValues.getTag(), throwable);
                }
            }
        });
    }


    public EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> getRequestValues(int pulltype, int pageIndex) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.adminGroupImageInfoList(gategoryId, pageIndex));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>(pulltype, easyCall, CacheMode.LOAD_NETWORK_ONLY);
        return requestValues;
    }

}
