package com.meizitu.mvp.presenter;

import com.meizitu.core.ImageDB;
import com.meizitu.internal.di.PerActivity;
import com.meizitu.mvp.contract.FavoritesListContract;
import com.meizitu.mvp.usecase.GetDatasFromDbUseCase;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easyclean.UseCaseHandler;
import cc.easyandroid.easyclean.presentation.presenter.base.EasyBasePresenter;


@PerActivity
public class FavoritesListPresenter extends EasyBasePresenter<FavoritesListContract.View> implements FavoritesListContract.Presenter {

    protected final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();

    protected final GetDatasFromDbUseCase<Item_GroupImageInfoListItem> mGetDatasFromDbUseCase;

    @Inject
    public FavoritesListPresenter(GetDatasFromDbUseCase<Item_GroupImageInfoListItem> getDatasFromDbUseCase) {
        this.mGetDatasFromDbUseCase = getDatasFromDbUseCase;
    }

    @Override
    public void attachView(FavoritesListContract.View view) {
        super.attachView(view);
        view.setPresenter(this);
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
                    ResponseInfo<Paging<List<Item_GroupImageInfoListItem>>> responseInfo = new ResponseInfo<>();
                    responseInfo.setCode("C0000");
                    Paging<List<Item_GroupImageInfoListItem>> paging = new Paging<>();
                    paging.setData(item_groupImageInfoListItems);
                    responseInfo.setData(paging);
                    getView().onSimpleListSuccess("", responseInfo);
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


    @Override
    public void executeSimpleListRequest(int pulltype, int pageIndex  ) {
        execute();
    }
}
