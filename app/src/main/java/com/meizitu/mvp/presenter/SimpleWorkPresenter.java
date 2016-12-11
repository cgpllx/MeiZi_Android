package com.meizitu.mvp.presenter;

import cc.easyandroid.easyclean.UseCase;

import cc.easyandroid.easyclean.UseCaseHandler;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyclean.presentation.presenter.base.EasyBasePresenter;
import cc.easyandroid.easyclean.presentation.view.IEasyView;
import cc.easyandroid.easyclean.repository.EasyWorkRepository;


public class SimpleWorkPresenter<V extends IEasyView> extends EasyBasePresenter<V> {

    protected final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();

//    //******一个EasyWorkUseCase一次只能进行一次请求，请求第二次时候会取消第一次的,所以他的作用范围只能是presenter******
    protected final EasyWorkUseCase mEasyWorkUseCase = new EasyWorkUseCase(new EasyWorkRepository());

    protected <T> void handleRequest(EasyWorkUseCase mEasyWorkUseCase,final EasyWorkUseCase.RequestValues<T> requestValues, UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<T>> useCaseCallback) {
        mUseCaseHandler.execute(mEasyWorkUseCase, requestValues, useCaseCallback);
    }

    public EasyWorkUseCase getDefaultEasyWorkUseCase() {
        return mEasyWorkUseCase;
    }

    @Override
    public void execute() {
    }

    @Override
    protected void onDetachView() {
        cancel();
    }

    @Override
    protected void onCancel() {
        super.onCancel();
        mEasyWorkUseCase.cancle();
    }


}
