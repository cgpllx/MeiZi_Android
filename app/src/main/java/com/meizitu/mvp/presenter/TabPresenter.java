package com.meizitu.mvp.presenter;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;

import java.util.List;

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
public class TabPresenter extends QfangEasyWorkPresenter<ResponseInfo<List<Category>>> {
    ImageApi imageApi;

    @Inject
    public TabPresenter(ImageApi imageApi) {
        super();
        this.imageApi = imageApi;
    }

    public void execute() {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.categoryList(30));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>("", easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        execute(requestValues);
    }
}


