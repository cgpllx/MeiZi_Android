package com.meizitu.mvp.presenter;

import com.meizitu.mvp.contract.SimpleListContract;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;

/**
 * 抽象的list列表的presenter,if you show list ，please extends AbsSimpleListPresenter
 */
public abstract class AbsSimpleListPresenter<T, V extends SimpleListContract.View<T>> extends SimpleWorkPresenter<V> implements SimpleListContract.Presenter<T, V> {

    @Override
    public void executeSimpleListRequest(int pulltype, int pageIndex,String cachecontrol) {
        final EasyWorkUseCase.RequestValues<T> requestValues = getRequestValues(pulltype, pageIndex,cachecontrol);
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

    protected abstract EasyWorkUseCase.RequestValues<T> getRequestValues(int pulltype, int pageIndex,String cachecontrol);

    @Override
    public void attachView(V view) {
        super.attachView(view);
        view.setPresenter(this);
    }

}


