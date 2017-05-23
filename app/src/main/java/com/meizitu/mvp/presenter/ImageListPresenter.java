package com.meizitu.mvp.presenter;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;

@PerActivity
public class ImageListPresenter extends AbsSimpleListPresenter<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>, ImageListContract.View> implements ImageListContract.Presenter {

    final int gategoryId;
    final ImageApi imageApi;
    final int requestTime;

    @Inject
    public ImageListPresenter(ImageApi imageApi, @Named("GategoryId")int gategoryId,  @Named("Time")int requestTime) {
        this.gategoryId = gategoryId;
        this.imageApi = imageApi;
        this.requestTime = requestTime;

    }


    @Override
    protected EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> getRequestValues(int pulltype, int pageIndex, String cachecontrol) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.queryGroupImageInfoList(gategoryId, pageIndex,requestTime));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>(pulltype, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        return requestValues;
    }
}
