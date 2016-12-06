package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.meizitu.GroupImageInfoListAdapter;
import com.meizitu.ImageApplication;
import com.meizitu.It.IToggle;
import com.meizitu.R;
import com.meizitu.internal.di.components.DaggerImageListComponent;
import com.meizitu.internal.di.components.ImageComponent;
import com.meizitu.internal.di.modules.ActivityModule;
import com.meizitu.internal.di.modules.ImageListModule;
import com.meizitu.items.Item_GroupImageInfoListItem;
import com.meizitu.mvp.contract.ImageListContract;
import com.meizitu.mvp.presenter.ImageListPresenter;
import com.meizitu.mvp.presenter.QfangEasyWorkPresenter;
import com.meizitu.pojo.Paging;
import com.meizitu.pojo.ResponseInfo;

import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;
import cc.easyandroid.easyclean.domain.easywork.EasyWorkUseCase;
import cc.easyandroid.easycore.EasyCall;
import cc.easyandroid.easyhttp.core.CacheMode;
import cc.easyandroid.easyhttp.retrofit2.RetrofitCallToEasyCall;
import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyutils.EasyToast;


public class ImageListFragment extends QfangFlexibleListFragment<Item_GroupImageInfoListItem> implements IToggle, ImageListContract.View {

    public static final String ID = "id";

    @Inject
    ImageListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        int id = getArguments().getInt(ID);
        DaggerImageListComponent.builder().applicationComponent(ImageApplication.get(getContext()).getApplicationComponent())
                .imageListModule(new ImageListModule(id)).build().inject(this);
        presenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoListItem> onCreateEasyRecyclerAdapter() {
        return new GroupImageInfoListAdapter(getContext(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.imagelist, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close:
                closeAll();
                break;
            case R.id.open:
                openAll();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeAll() {
        presenter.exeCloseAllRequest();
    }

    private void openAll() {
        presenter.exeOpenAllRequest();
    }

    @Override
    public void closeSingle(Item_GroupImageInfoListItem item_groupImageInfoListItem) {
        presenter.exeCloseSingleRequest(item_groupImageInfoListItem);
    }

    @Override
    public void openSingle(Item_GroupImageInfoListItem item_groupImageInfoListItem) {
        System.out.println("item_groupImageInfoListItem" + item_groupImageInfoListItem);
        presenter.exeOpenSingleRequest(item_groupImageInfoListItem);
    }

    @Override
    public void onCloseAllStart(Object o) {
        EasyToast.showShort(getContext(), "CLOSEALLonStart");
        showLoading("CLOSEALLonStart");
    }

    @Override
    public void onCloseAllError(Object o, Throwable throwable) {
        hideLoading();
        errorTip(o, throwable);
    }

    @Override
    public void onCloseAllSuccess(Object o, ResponseInfo t) {
        EasyToast.showShort(getContext(), "onCloseAllSuccess");
        hideLoading();
        autoRefresh();
    }

    @Override
    public void onOpenAllStart(Object o) {
        EasyToast.showShort(getContext(), "onOpenAllStart");
        showLoading("onOpenAllStart");
    }

    @Override
    public void onOpenAllError(Object o, Throwable throwable) {
        hideLoading();
        errorTip(o, throwable);
    }

    @Override
    public void onOpenAllSuccess(Object o, ResponseInfo t) {
        EasyToast.showShort(getContext(), "onOpenAllSuccess");
        hideLoading();
        autoRefresh();
    }

    @Override
    public void onCloseSingleStart(Object o) {
        EasyToast.showShort(getContext(), "onCloseSingleStart");
        showLoading("onCloseSingleStart");
    }

    @Override
    public void onCloseSingleError(Object o, Throwable throwable) {
        EasyToast.showShort(getContext(), throwable != null ? throwable.getMessage() : "");
        hideLoading();
    }

    @Override
    public void onCloseSingleSuccess(Item_GroupImageInfoListItem item_groupImageInfoListItem, ResponseInfo t) {
        if (t != null && t.isSuccess()) {
            item_groupImageInfoListItem.setStatus(false);
            helper.getRecyclerAdapter().updateItem(item_groupImageInfoListItem, null);
            EasyToast.showShort(getContext(), "CLOSESINGLEonSuccess");
        }
        hideLoading();
    }

    @Override
    public void onOpenSingleStart(Object o) {
        EasyToast.showShort(getContext(), "onOpenSingleStart");
        showLoading("onOpenSingleStart");
    }

    @Override
    public void onOpenSingleError(Object o, Throwable throwable) {
        hideLoading();
        errorTip(o, throwable);
    }

    @Override
    public void onOpenSingleSuccess(Item_GroupImageInfoListItem item_groupImageInfoListItem, ResponseInfo t) {
        hideLoading();
        if (t != null && t.isSuccess()) {
            item_groupImageInfoListItem.setStatus(true);
            System.out.println("item_groupImageInfoListItem" + item_groupImageInfoListItem);
            helper.getRecyclerAdapter().updateItem(item_groupImageInfoListItem, null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
