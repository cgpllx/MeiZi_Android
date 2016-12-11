package com.meizitu.mvp.presenter;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.LoginContract;
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

/**
 * Created by chenguoping on 16/12/4.
 */
@PerActivity
public class LoginPresenter extends LoginContract.Presenter {
    ImageApi imageApi;

    @Inject
    public LoginPresenter(ImageApi imageApi) {
        super();
        this.imageApi = imageApi;
    }

    public void doLogin(String username, String password) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.login(username, password));
        final EasyWorkUseCase.RequestValues<ResponseInfo> requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ONLY);
        if (isViewAttached())
            getView().onLoginStart(requestValues.getTag());
        handleRequest(getDefaultEasyWorkUseCase(), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo> responseValue) {
                if (isViewAttached())
                    getView().onLoginSuccess(responseValue.getTag(), responseValue.getData());
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached())
                    getView().onLoginError(requestValues.getTag(), throwable);
            }
        });
    }
}


