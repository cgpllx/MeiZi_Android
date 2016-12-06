package com.meizitu.mvp.presenter;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;

/**
 *
 */
@PerActivity
public class SimpleListPresenter<T> extends QfangEasyWorkPresenter<ResponseInfo<Paging<List<T>>>> {
    final ImageApi imageApi;
    final int id;

    @Inject
    public SimpleListPresenter(ImageApi imageApi, int id) {
        super();
        this.imageApi = imageApi;
        this.id = id;
    }

    public void executeRequest(int pulltype, int pageIndex) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.adminGroupImageInfoList(id, pageIndex));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>(pulltype, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        execute(requestValues);
    }
}


