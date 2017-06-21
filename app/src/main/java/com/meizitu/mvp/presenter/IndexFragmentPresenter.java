package com.meizitu.mvp.presenter;

import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.IndexFragmentContract;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.service.ImageApi;
import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easyclean.repository.EasyWorkRepository;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;

@PerActivity
public class IndexFragmentPresenter extends AbsSimpleListPresenter<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>, IndexFragmentContract.View> implements IndexFragmentContract.Presenter {

    final int id;
    final ImageApi imageApi;
    final int time;

    @Inject
    public IndexFragmentPresenter(ImageApi imageApi, int id, @Named("Time") int time) {
        this.id = id;
        this.imageApi = imageApi;
        this.time = time;
    }

    @Override
    public void execute() {
        super.execute();
        final EasyWorkUseCase.RequestValues<ResponseInfo<List<Item_CategoryInfoItem>>> requestValues = getCategoryRequestValues();
        if (isViewAttached()) {
            getView().onCategoryListStart(requestValues.getTag());
        }
        handleRequest(new EasyWorkUseCase(new EasyWorkRepository()), requestValues, new UseCase.UseCaseCallback<EasyWorkUseCase.ResponseValue<ResponseInfo<List<Item_CategoryInfoItem>>>>() {
            @Override
            public void onSuccess(EasyWorkUseCase.ResponseValue<ResponseInfo<List<Item_CategoryInfoItem>>> responseInfoResponseValue) {
                if (isViewAttached()) {
                    getView().onCategoryListSuccess(requestValues.getTag(), responseInfoResponseValue.getData());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached()) {
                    getView().onCategoryListError(requestValues.getTag(), throwable);
                }
            }
        });
    }

    private EasyWorkUseCase.RequestValues<ResponseInfo<List<Item_CategoryInfoItem>>> getCategoryRequestValues() {
        EasyCall<ResponseInfo<List<Item_CategoryInfoItem>>> easyCall = new RetrofitCallToEasyCall<>(imageApi.categoryList(id));
        EasyWorkUseCase.RequestValues<ResponseInfo<List<Item_CategoryInfoItem>>> requestValues = new EasyWorkUseCase.RequestValues<>(0, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        return requestValues;
    }

    @Override
    protected EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> getRequestValues(int pulltype, int pageIndex) {
        EasyCall<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> easyCall = new RetrofitCallToEasyCall<>(imageApi.queryLatestGroupImageList(pageIndex, time));
        EasyWorkUseCase.RequestValues<ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>>> requestValues = new EasyWorkUseCase.RequestValues<>(0, easyCall, CacheMode.LOAD_NETWORK_ELSE_CACHE);
        return requestValues;
    }
}
