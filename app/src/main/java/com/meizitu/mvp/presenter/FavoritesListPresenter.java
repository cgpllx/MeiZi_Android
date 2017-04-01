package com.meizitu.mvp.presenter;

import com.meizitu.core.ImageDB;
import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.FavoritesListContract;
import com.meizitu.mvp.usecase.GetDatasFromDbUseCase;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.UseCaseHandler;
import cc.easyandroid.easyclean.presentation.presenter.base.EasyBasePresenter;
import cc.easyandroid.easyutils.ArrayUtils;


@PerActivity
public class FavoritesListPresenter extends EasyBasePresenter<FavoritesListContract.View> implements FavoritesListContract.Presenter {

    protected final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();

    protected final GetDatasFromDbUseCase<Item_GroupImageInfoListItem> mGetDatasFromDbUseCase;

    @Inject
    public FavoritesListPresenter(GetDatasFromDbUseCase<Item_GroupImageInfoListItem> getDatasFromDbUseCase) {
        this.mGetDatasFromDbUseCase = getDatasFromDbUseCase;
    }


    protected void handleRequest(GetDatasFromDbUseCase mGetDatasFromDbUseCase, final GetDatasFromDbUseCase.RequestValues requestValues, UseCase.UseCaseCallback<GetDatasFromDbUseCase.ResponseValue<Item_GroupImageInfoListItem>> useCaseCallback) {
        mUseCaseHandler.execute(mGetDatasFromDbUseCase, requestValues, useCaseCallback);
    }

    @Override
    public void execute() {
        if (isViewAttached()) {
            getView().onSimpleListStart("");
        }
        handleRequest(mGetDatasFromDbUseCase, new GetDatasFromDbUseCase.RequestValues(ImageDB.TABNAME_GROUPIMAGEINFO, Item_GroupImageInfoListItem.class), new UseCase.UseCaseCallback<GetDatasFromDbUseCase.ResponseValue<Item_GroupImageInfoListItem>>() {
            @Override
            public void onSuccess(GetDatasFromDbUseCase.ResponseValue<Item_GroupImageInfoListItem> item_groupImageInfoListItemResponseValue) {
                ArrayList<Item_GroupImageInfoListItem> item_groupImageInfoListItems = item_groupImageInfoListItemResponseValue.getDatas();
                if (isViewAttached()) {
                    if (!ArrayUtils.isEmpty(item_groupImageInfoListItems)) {
                        getView().onSimpleListSuccess("", item_groupImageInfoListItems);
                    } else {
                        onError(new IOException("empty"));
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (isViewAttached()) {
                    getView().onSimpleListError("", throwable);
                }
            }
        });
    }

    @Override
    protected void onDetachView() {
        cancel();
    }

    @Override
    protected void onCancel() {
        super.onCancel();
    }


}
