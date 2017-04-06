package com.meizitu.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.meizitu.R;
import com.meizitu.internal.di.components.IndexFragmentComponent;
import com.meizitu.mvp.contract.IndexFragmentContract;
import com.meizitu.mvp.presenter.IndexFragmentPresenter;
import com.meizitu.pojo.ResponseInfo;
import com.meizitu.ui.items.Item_CategoryInfoItem;
import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import java.util.List;

import javax.inject.Inject;


public class IndexFragment extends  BaseListFragment<Item_GroupImageInfoListItem> implements IndexFragmentContract.View {
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
    protected void onQfangViewCreated(View view, Bundle savedInstanceState) {
        super.onQfangViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);
        getComponent(IndexFragmentComponent.class).inject(this);
        presenter.attachView(this);

    }
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        simpleRecyclerView = EasyViewUtil.findViewById(view, R.id.qfangRecyclerView);
//        simpleRecyclerView.setLoadMoreEnabled(false);
//        simpleRecyclerView.setRefreshEnabled(false);
////        simpleRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        simpleRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
////        simpleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        simpleRecyclerView.setHasFixedSize(true);
//
//        simpleRecyclerView.setAdapter(adapter);
//        setUserVisibleHint(true);
//        getComponent(IndexFragmentComponent.class).inject(this);
//        presenter.attachView(this);
//        presenter.execute();
//    }

    @Override
    public void onCategoryListStart(Object o) {
//        simpleRecyclerView.showLoadingView();
    }

    @Override
    public void onCategoryListError(Object o, Throwable t) {
//        simpleRecyclerView.showErrorView();
    }

    @Override
    public void onCategoryListSuccess(Object o, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
        deliverCategoryResult(o, responseInfo);
    }

    public void deliverCategoryResult(Object i, ResponseInfo<List<Item_CategoryInfoItem>> responseInfo) {
        List<Item_CategoryInfoItem> list = responseInfo.getData();
//        adapter.addItems(new ArrayList<>(list));
//        adapter.addFooterItem(new Item_Index_LatestImage());
    }

}
