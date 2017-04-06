package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.meizitu.R;
import com.meizitu.adapter.GroupImageInfoListAdapter;
import com.meizitu.adapter.IndexListAdapter;
import com.meizitu.internal.di.components.ImageListComponent;
import com.meizitu.internal.di.components.IndexFragmentComponent;
import com.meizitu.mvp.contract.IndexFragmentContract;
import com.meizitu.mvp.presenter.IndexFragmentPresenter;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;
import com.meizitu.ui.items.Item_Index_LatestImage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cc.easyandroid.easyrecyclerview.EasyFlexibleAdapter;


public class IndexFragment extends BaseListFragment<Item_GroupImageInfoListItem> implements IndexFragmentContract.View {
    @Inject
    IndexFragmentPresenter presenter;


//    EasyFlexibleAdapter<Item_GroupImageInfoListItem> adapter = new EasyFlexibleAdapter<>();

    public static Fragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_simple_list;
    }



    @Override
    protected void onQfangViewPrepared(View view, Bundle savedInstanceState) {
        super.onQfangViewPrepared(view, savedInstanceState);
//        simpleRecyclerView.setRefreshEnabled(false);
//        simpleRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        helper.setRefreshEnabled(false);
        gridLayoutManager.setSpanCount(6);
        if(helper.getRecyclerAdapter().getHeaderItemCount()<2){
            presenter.execute();
            System.out.println("list="+helper.getRecyclerAdapter().getHeaderItemCount());
        }

    }
    @Override
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);
        getComponent(IndexFragmentComponent.class).inject(this);
        presenter.attachView(this);
    }

    @Override
    protected EasyFlexibleAdapter<Item_GroupImageInfoListItem> onCreateEasyRecyclerAdapter() {
        return new IndexListAdapter();
    }

    @Override
    public void onCategoryListStart(Object o) {
//        simpleRecyclerView.showLoadingView();
        System.out.println("list="+"onCategoryListStart");
    }

    @Override
    public void onCategoryListError(Object o, Throwable t) {
//        simpleRecyclerView.showErrorView();
        System.out.println("list="+"onCategoryListError");
    }

    @Override
    public void onCategoryListSuccess(Object o, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
        System.out.println("list="+"onCategoryListSuccess");
        deliverCategoryResult(o, responseInfo);
    }

    public void deliverCategoryResult(Object i, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
        ArrayList list = new ArrayList<>(responseInfo.getData());
        helper.getRecyclerAdapter().addHeaderItems(list);
        System.out.println("list="+list);
//        setUserVisibleHint(true);
//        adapter.addItems(new ArrayList<>(list));
        helper.getRecyclerAdapter().addHeaderItem(new Item_Index_LatestImage());
    }

}
