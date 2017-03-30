package com.meizitu.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;

import com.meizitu.R;
import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.mvp.contract.IndexFragmentContract;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;
import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import cc.easyandroid.easyutils.EasyToast;

@PerActivity
public class IndexFragmentPresenter extends AbsSimpleListPresenter<ResponseInfo<Paging<List<Item_CategoryInfoItem>>>, IndexFragmentContract.View> implements IndexFragmentContract.Presenter {

    final int id;
    final ImageApi imageApi;

    @Inject
    public IndexFragmentPresenter(ImageApi imageApi, int id) {
        this.id = id;
        this.imageApi = imageApi;
    }


    @Override
    protected EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_CategoryInfoItem>>>> getRequestValues(int pulltype, int pageIndex, String cachecontrol) {
        EasyCall easyCall = new RetrofitCallToEasyCall<>(imageApi.categoryList(id));
        EasyWorkUseCase.RequestValues requestValues = new EasyWorkUseCase.RequestValues<>(pulltype, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        return requestValues;
    }



}
