package com.meizitu.mvp.presenter;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;

import javax.inject.Inject;

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
public class LoginPresenter extends EasyWorkPresenter<ResponseInfo> {
    ImageApi imageApi;

    @Inject
    public LoginPresenter(ImageApi imageApi) {
        super((new EasyWorkUseCase<ResponseInfo>(new EasyWorkRepository())));
        this.imageApi = imageApi;
    }

    public void doLogin(String username, String password) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.login(username, password));
        execute(new EasyWorkUseCase.RequestValues("", easyCall, CacheMode.LOAD_NETWORK_ONLY));
    }
}


