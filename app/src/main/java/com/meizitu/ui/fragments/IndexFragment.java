package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.meizitu.adapter.IndexListAdapter;
import com.meizitu.core.EasyFlexibleRecyclerViewHelper;
import com.meizitu.internal.di.components.MainActivityComponent;
import com.meizitu.mvp.contract.IndexFragmentContract;
import com.meizitu.mvp.presenter.IndexFragmentPresenter;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;
import com.meizitu.ui.items.Item_Index_NewCategory;
import com.meizitu.ui.items.Item_Index_LatestImage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;
import cc.easyandroid.easyutils.ArrayUtils;


public class IndexFragment extends BaseListFragment<Item_GroupImageInfoListItem> implements IndexFragmentContract.View {
    @Inject
    IndexFragmentPresenter presenter;

    public static Fragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    protected void onQfangViewPrepared(View view, Bundle savedInstanceState) {
        super.onQfangViewPrepared(view, savedInstanceState);
        helper.setRefreshEnabled(false);
        gridLayoutManager.setSpanCount(6);
        if(helper.getRecyclerAdapter().getHeaderItemCount()<2){
//            presenter.execute();
        }

    }


    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);
        getComponent(MainActivityComponent.class).inject(this);
        presenter.attachView(this);
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoListItem> onCreateEasyRecyclerAdapter() {
        return new IndexListAdapter();
    }

    @Override
    public void onCategoryListStart(Object o) {
        onSimpleListStart(o);
    }

    @Override
    public void onCategoryListError(Object o, Throwable t) {
        onSimpleListError(o,t);
    }

    @Override
    protected void refesh() {
//        super.refesh();
        presenter.execute();

    }

    @Override
    public void onCategoryListSuccess(Object o, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
        deliverCategoryResult(o, responseInfo);
    }

    public void deliverCategoryResult(Object i, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
        if(responseInfo!=null){
            helper.getRecyclerAdapter().addHeaderItem(new Item_Index_NewCategory());
            ArrayList list = new ArrayList<>(responseInfo.getData());
//            list.add(new Item_Index_LatestImage());
            helper.getRecyclerAdapter().addItems(list);
            helper.getRecyclerAdapter().notifyDataSetChanged();
//            helper.getRecyclerAdapter().addItem(new Item_Index_LatestImage());
//            execute(EasyFlexibleRecyclerViewHelper.LOADMORE);
        }
    }

}
