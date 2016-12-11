package com.meizitu.mvp.presenter;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.TabContract;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyclean.presentation.presenter.EasyWorkPresenter;
import cc.easyandroid.easyclean.repository.EasyWorkRepository;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;

@PerActivity
public class TabPresenter extends TabContract.Presenter {
    ImageApi imageApi;

    @Inject
    public TabPresenter(ImageApi imageApi) {
        super();
        this.imageApi = imageApi;
    }

    @Override
    public void execute() {
        EasyCall<ResponseInfo<List<Category>>> easyCall = new RetrofitCallToEasyCall<>(imageApi.categoryList(30));
        final EasyWorkUseCase.RequestValues<ResponseInfo<List<Category>>> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        if (isViewAttached()) {
            getView().onTabLoadStart(requestValues.getTag());
        }
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo<List<Category>>>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo<List<Category>>> responseValue) {
                if (isViewAttached()) {
                    getView().onTabLoadSuccess(requestValues.getTag(), responseValue.getData());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached())
                    getView().onTabLoadError(requestValues.getTag(), throwable);
            }
        });
    }
}


