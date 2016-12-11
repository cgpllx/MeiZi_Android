package com.meizitu.mvp.presenter;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.SimpleListContract;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;

/**
 *
 */
public abstract class AbsSimpleListPresenter<T, V extends SimpleListContract.View<T>> extends SimpleListContract.Presenter<T, V> {

    @Override
    public void executeSimpleListRequest(int pulltype, int pageIndex) {
        final EasyWorkUseCase.RequestValues<T> requestValues = getRequestValues(pulltype, pageIndex);
        if (isViewAttached()) {
            getView().onSimpleListStart(requestValues.getTag());
        }
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<T>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<T> responseValue) {
                if (isViewAttached()) {
                    getView().onSimpleListSuccess(responseValue.getTag(), responseValue.getData());
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

    protected abstract EasyWorkUseCase.RequestValues<T> getRequestValues(int pulltype, int pageIndex);
}


