package com.meizitu.mvp.presenter;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.IndexFragmentContract;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;
import com.meizitu.ui.items.Item_CategoryInfoItem;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;

@PerActivity
public class IndexFragmentPresenter extends SimpleWorkPresenter<IndexFragmentContract.View> implements IndexFragmentContract.Presenter {

    final int id;
    final ImageApi imageApi;

    @Inject
    public IndexFragmentPresenter(ImageApi imageApi, int id) {
        this.id = id;
        this.imageApi = imageApi;
    }

    @Override
    public void execute() {
        super.execute();
        final EasyWorkUseCase.RequestValues<ResponseInfo<List<Item_CategoryInfoItem>>> requestValues = getRequestValues();
        if (isViewAttached()) {
            getView().onSimpleListStart(requestValues.getTag());
        }
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo<List<Item_CategoryInfoItem>>>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo<List<Item_CategoryInfoItem>>> responseInfoResponseValue) {
                if (isViewAttached()) {
                    getView().onSimpleListSuccess(requestValues.getTag(), responseInfoResponseValue.getData());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached()) {
                    getView().onSimpleListError(requestValues.getTag(), throwable);
                }
            }
        });
    }

    protected EasyWorkUseCase.RequestValues<ResponseInfo<List<Item_CategoryInfoItem>>> getRequestValues() {
        EasyCall<ResponseInfo<List<Item_CategoryInfoItem>>> easyCall = new RetrofitCallToEasyCall<>(imageApi.categoryList(id));
        EasyWorkUseCase.RequestValues<ResponseInfo<List<Item_CategoryInfoItem>>> requestValues = new EasyWorkUseCase.RequestValues<>(0, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        return requestValues;
    }


}
