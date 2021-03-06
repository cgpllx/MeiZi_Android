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

    final int categoryId;
    final ImageApi imageApi;
    final int requestTime;

    @Inject
    public ImageListPresenter(ImageApi imageApi, @Named("CategoryId")int categoryId,  @Named("Time")int requestTime,ImageListContract.View view) {
        this.categoryId = categoryId;
        this.imageApi = imageApi;
        this.requestTime = requestTime;
        attachView(view);
    }


    @Override
    protected EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> getRequestValues(int pulltype, int pageIndex) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.queryGroupImageInfoList(categoryId, pageIndex,requestTime));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>(pulltype, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        return requestValues;
    }

    public int getCategoryId(){
        return categoryId;
    }
}
